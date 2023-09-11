package com.tasty.app.service.impl;

import com.tasty.app.config.RestTemplateResponseErrorHandler;
import com.tasty.app.domain.*;
import com.tasty.app.exception.BadRequestException;
import com.tasty.app.repository.*;
import com.tasty.app.repository.projection.PostsDetail;
import com.tasty.app.request.PostsRequest;
import com.tasty.app.response.*;
import com.tasty.app.service.ImageService;
import com.tasty.app.service.PostService;
import com.tasty.app.service.dto.FileDTO;
import com.tasty.app.service.dto.ImageDTO;
import com.tasty.app.service.dto.PostDTO;
import com.tasty.app.web.rest.errors.BadRequestAlertException;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.tasty.app.constant.Constant.*;
import static com.tasty.app.domain.enumeration.TypeOfImage.DISH;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

/**
 * Service Implementation for managing {@link Post}.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    private final StepToCookRepository stepToCookRepository;

    private final DishTypeRepository dishTypeRepository;

    private final IngredientOfDishRepository ingredientOfDishRepository;
    private final CustomerRepository customerRepository;
    private final ImageRepository imageRepository;
    private final TypeOfDishRepository typeOfDishRepository;
    private final IngredientRepository ingredientRepository;
    private final FavoritesRepository favoritesRepository;
    private final CommentRepository commentRepository;
    private final EvaluationRepository evaluationRepository;
    private final MinioService minioService;
    private final ImageService imageService;
    private final RestTemplateBuilder templateBuilder;

    public PostServiceImpl(PostRepository postRepository, StepToCookRepository stepToCookRepository, DishTypeRepository dishTypeRepository, IngredientOfDishRepository ingredientRepository, CustomerRepository customerRepository, ImageRepository imageRepository, TypeOfDishRepository typeOfDishRepository, IngredientRepository ingredientRepository1, FavoritesRepository favoritesRepository, CommentRepository commentRepository, EvaluationRepository evaluationRepository, MinioService minioService, ImageService imageService, RestTemplateBuilder templateBuilder) {
        this.postRepository = postRepository;
        this.stepToCookRepository = stepToCookRepository;
        this.dishTypeRepository = dishTypeRepository;
        this.ingredientOfDishRepository = ingredientRepository;
        this.customerRepository = customerRepository;
        this.imageRepository = imageRepository;
        this.typeOfDishRepository = typeOfDishRepository;
        this.ingredientRepository = ingredientRepository1;
        this.favoritesRepository = favoritesRepository;
        this.commentRepository = commentRepository;
        this.evaluationRepository = evaluationRepository;
        this.minioService = minioService;
        this.imageService = imageService;
        this.templateBuilder = templateBuilder;
    }

    @Override
    public Post save(Post post) {
        log.debug("Request to save Post : {}", post);
        return postRepository.save(post);
    }

    @Override
    public Post update(Post post) {
        log.debug("Request to update Post : {}", post);
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> partialUpdate(Post post) {
        log.debug("Request to partially update Post : {}", post);

        return postRepository
            .findById(post.getId())
            .map(existingPost -> {
                if (post.getTitle() != null) {
                    existingPost.setTitle(post.getTitle());
                }
                if (post.getContent() != null) {
                    existingPost.setContent(post.getContent());
                }
                if (post.getDescription() != null) {
                    existingPost.setDescription(post.getDescription());
                }
                if (post.getStatus() != null) {
                    existingPost.setStatus(post.getStatus());
                }
                if (post.getCreatedDate() != null) {
                    existingPost.setCreatedDate(post.getCreatedDate());
                }

                return existingPost;
            })
            .map(postRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> findAll(Pageable pageable) {
        log.debug("Request to get all Posts");
        return postRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Post> findOne(Long id) {
        log.debug("Request to get Post : {}", id);
        return postRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Post : {}", id);
        postRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> getPosts(String keyword, Pageable pageable) {
        Page<Post> postList = postRepository.getAllPosts(keyword, pageable);
        List<PostsResponse> data = getListPostsResponse(postList.getContent());
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("totalPage", postList.getTotalPages());
        return result;
    }

    @Override
    public Map<String, Object> getPostsByUsername(String username) {
        List<Post> postList = postRepository.findAllByAuthor_Username(username);
        List<PostsResponse> data = getListPostsResponse(postList);
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("totalPage", 1);
        return result;
    }

    public List<PostsResponse> getListPostsResponse(List<Post> postList) {
        return postList.stream().map(this::convertToPostResponse).collect(Collectors.toList());
    }

    public PostsResponse convertToPostResponse(Post post) {
        Map rating = evaluationRepository.getRate(post.getId());
        long totalPoint = Objects.isNull(rating.get("totalPoint")) ? 0l : (long) rating.get("totalPoint");
        long totalReview = (long) rating.get("totalReview");
        Image image = imageRepository.findByTypeAndPost(DISH, post);
        return new PostsResponse(
            post.getId(),
            post.getTitle(),
            dishTypeRepository.getAllByPostId(post.getId()),
            Objects.isNull(image) ? "https://icon-library.com/images/meat-icon-png/meat-icon-png-11.jpg" : image.getUri(),
            totalPoint == 0l ? 0d : (double) totalPoint / totalReview,
            totalReview
        );
    }

    @Override
    public Post createPost(PostsRequest request) {
        Post post = new Post();
        // TODO: Lấy username từ token
        String username = "tiennd";
        // TODO: Lưu ảnh vào minio
        String imageUrl = "";
        if (Objects.isNull(request.getId())) {
            post.setCreatedDate(LocalDate.now());
            Customer customer = customerRepository.findByUsername(username);
            post.setAuthor(customer);
        } else {
            post = postRepository.getReferenceById(request.getId());
            imageRepository.deleteAllByPost_IdAndType(request.getId(), DISH);
            typeOfDishRepository.deleteAllByPost_Id(post.getId());
            stepToCookRepository.deleteAllByPost(post);
            ingredientOfDishRepository.deleteAllByPost_Id(post.getId());
        }
        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        post.setStatus(true);

        post = postRepository.save(post);

        Image image = new Image();
        image.setType(DISH);
        image.setUri(imageUrl);
        image.setPost(post);
        imageRepository.save(image);

        List<TypeOfDish> allNewType = new ArrayList<>();
        List<DishType> dishTypes = dishTypeRepository.findByListName(request.getTypes());
        Post finalPost = post;
        dishTypes.forEach(dishType -> allNewType.add(new TypeOfDish()
            .type(dishType)
            .post(finalPost)
        ));
        typeOfDishRepository.saveAll(allNewType);

        List<StepToCook> allSteps = new ArrayList<>();
        AtomicLong index = new AtomicLong(1);
        request.getSteps().forEach(step -> allSteps.add(
            new StepToCook()
                .content(step.getContent())
                .index(index.getAndIncrement())
                .post(finalPost)
        ));
        stepToCookRepository.saveAll(allSteps);

        List<IngredientOfDish> allIngredient = new ArrayList<>();
        request.getIngredient().forEach(i -> {
            Ingredient ingredient = ingredientRepository.findByName(i.getName());
            if (Objects.isNull(ingredient)) {
                ingredient = new Ingredient()
                    .name(i.getName());
                ingredient = ingredientRepository.save(ingredient);
            }
            allIngredient.add(
                new IngredientOfDish().unit(i.getUnit())
                    .quantity(i.getQuantity().longValue())
                    .post(finalPost)
                    .ingredient(ingredient)
            );
        });
        ingredientOfDishRepository.saveAll(allIngredient);

        return post;
    }

    @Override
    public Post updatePost(PostDTO dto) {
        Post post = postRepository.getReferenceById(dto.getId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setDescription(dto.getDescription());
        post.setStatus(dto.getStatus());

        postRepository.save(post);
        stepToCookRepository.deleteAllByPost(post);

        for (PostDTO.Step step : dto.getSteps()) {
            StepToCook stepToCook = new StepToCook().content(step.getContent())
                .index(step.getIndex());

            stepToCookRepository.save(stepToCook);
        }
        return post;
    }

    @Override
    public String removePost(Long id) {
        Post post = postRepository.findById(id).orElse(new Post());
        if (Objects.isNull(post.getId())) {
            throw new BadRequestAlertException("Không tìm thấy bài viết.", "posts", "postsnotfound");
        }
        stepToCookRepository.deleteAllByPost(post);
        ingredientOfDishRepository.deleteAllByPost_Id(post.getId());
        favoritesRepository.deleteAllByPost(post);
        commentRepository.deleteAllByPost(post);
        evaluationRepository.deleteAllByPost(post);
        imageRepository.deleteAllByPost_IdAndType(post.getId(), DISH);
        typeOfDishRepository.deleteAllByPost_Id(post.getId());
        postRepository.delete(post);
        return "Success.";
    }

    @Override
    public PostsDetailResponse getDetail(Long id) {
        PostsDetail postsDetail = postRepository.getDetail(id);
        if (Objects.isNull(postsDetail)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy bài viết.");
        }
        List<DishType> dishTypes = dishTypeRepository.getAllType(id);
        List<StepToCook> steps = stepToCookRepository.findAllByPost_IdOrderByIndexAsc(id);
        List<IngredientOfDish> ingredients = ingredientOfDishRepository.findAllByPosts(id);
        return new PostsDetailResponse(
            postsDetail.getId(),
            postsDetail.getTitle(),
            dishTypes.stream().map(DishType::getName).collect(Collectors.toList()),
            postsDetail.getDescription(),
            Strings.isBlank(postsDetail.getImageUrl()) ? "https://icon-library.com/images/meat-icon-png/meat-icon-png-11.jpg" : postsDetail.getImageUrl(),
            Objects.nonNull(postsDetail.getRating()) ? postsDetail.getRating() : 0D,
            postsDetail.getTotalReviews(),
            steps.stream().map(s -> new StepResponse(s.getId(), s.getContent())).collect(Collectors.toList()),
            ingredients.stream().map(i -> new IngredientResponse(
                i.getIngredient().getId(),
                i.getIngredient().getName(),
                i.getUnit(),
                i.getQuantity())
            ).collect(Collectors.toList())
        );
    }

    @Override
    public void updateImage(FileDTO dto, Long postsId) {
        String imageUrl = minioService.uploadFile(dto);
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setPostsId(postsId);
        imageDTO.setType(DISH);
        imageDTO.setUri(imageUrl);
        imageService.createImage(imageDTO);
    }

    @Override
    public ResponseEntity findByImage(FileDTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(List.of(MediaType.ALL, MediaType.APPLICATION_JSON));
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("filename", dto.getFile().getResource());
        map.add("g-recaptcha-response", LOG_MEAL_RECAPTCHA);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = templateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        ResponseEntity<LogMealResponse> result = restTemplate.exchange(LOG_MEAL_API, POST, entity, LogMealResponse.class);
        return ResponseEntity.ok(result.getBody());
    }

    @Override
    public ResponseEntity findByImageCalorie(FileDTO dto) {
        List<String> recognitionResult = calorieMamaRecognition(dto.getFile());
        List<Post> allPosts = postRepository.findAll();
//        List<PostsResponse> response = allPosts.stream().filter(post ->
//            Arrays.stream(post.getOtherName().split(",")).anyMatch(on ->
//                recognitionResult.stream().anyMatch(on::equalsIgnoreCase)
//            )
//        ).map(this::convertToPostResponse).collect(Collectors.toList());

        Set<Post> filteredPosts = new HashSet<>();
        recognitionResult.forEach(r ->
            filteredPosts.addAll(
                allPosts.stream().filter(posts ->
                    Arrays.stream(posts.getOtherName().split(",")).anyMatch(r::equalsIgnoreCase)
                ).collect(Collectors.toList())
            )
        );
        List<PostsResponse> response = filteredPosts.stream().map(this::convertToPostResponse).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    public List<String> calorieMamaRecognition(MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);
        headers.add("accept", MediaType.ALL_VALUE);
        headers.add("authority", "www.caloriemama.ai");
        headers.add("origin", "https://www.caloriemama.ai");
        headers.add("referer", "https://www.caloriemama.ai/api");
        headers.add("cookie", "mp_c8ac40b34bef058fe76cd11db4cdec6e_mixpanel=%7B%22distinct_id%22%3A%20%22%24device%3A18a550bf8905ee-07cee7e10aacc2-26031f51-100200-18a550bf8915ee%22%2C%22%24device_id%22%3A%20%2218a550bf8905ee-07cee7e10aacc2-26031f51-100200-18a550bf8915ee%22%2C%22%24search_engine%22%3A%20%22google%22%2C%22%24initial_referrer%22%3A%20%22https%3A%2F%2Fwww.google.com%2F%22%2C%22%24initial_referring_domain%22%3A%20%22www.google.com%22%7D; _ga=GA1.2.1824110598.1693643963; _gid=GA1.2.1989766158.1693643963; _gat_gtm.js=1; _fbp=fb.1.1693643964236.371521729");
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("media", file.getResource());
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = templateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        ResponseEntity<CalorieResponse> result = restTemplate.exchange(CALORIE_API, POST, entity, CalorieResponse.class);
        CalorieResponse calorieResponse = result.getBody();
        assert calorieResponse != null;
        if (!calorieResponse.isFood()) {
            throw new BadRequestException("Hình ảnh truyền vào không thể xác định là một món ăn.");
        }
        List<String> allGroup = new ArrayList<>();
        calorieResponse.getResults().forEach(r -> {
            allGroup.add(r.getGroup());
            allGroup.addAll(r.getItems().stream().map(ResultItem::getName).collect(Collectors.toList()));
        });
        return allGroup.size() <= 60 ? allGroup : allGroup.subList(0, 60);
    }
}
