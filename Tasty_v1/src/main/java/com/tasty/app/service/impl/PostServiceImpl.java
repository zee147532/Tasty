package com.tasty.app.service.impl;

import com.tasty.app.domain.*;
import com.tasty.app.repository.*;
import com.tasty.app.repository.projection.PostsDetail;
import com.tasty.app.request.PostsRequest;
import com.tasty.app.response.*;
import com.tasty.app.service.PostService;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.tasty.app.service.dto.PostDTO;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static com.tasty.app.domain.enumeration.TypeOfImage.DISH;

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

    public PostServiceImpl(PostRepository postRepository, StepToCookRepository stepToCookRepository, DishTypeRepository dishTypeRepository, IngredientOfDishRepository ingredientRepository, CustomerRepository customerRepository, ImageRepository imageRepository, TypeOfDishRepository typeOfDishRepository, IngredientRepository ingredientRepository1) {
        this.postRepository = postRepository;
        this.stepToCookRepository = stepToCookRepository;
        this.dishTypeRepository = dishTypeRepository;
        this.ingredientOfDishRepository = ingredientRepository;
        this.customerRepository = customerRepository;
        this.imageRepository = imageRepository;
        this.typeOfDishRepository = typeOfDishRepository;
        this.ingredientRepository = ingredientRepository1;
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
        List<PostsResponse> data = postList.stream().map(p -> new PostsResponse(
            p.getId(),
            p.getContent(),
            List.of("abc", "123"),
            "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAoHCBUVFBcVFRUYGBcZGhoaGhkYGhoeHh0dHR0ZGh0eIxodICwjIh0pIiAgJDYkKS0yMzMzGiI4PjgyPSwyMy8BCwsLDw4PHhISHjIqIyoyMjI0NTQyMjQyNDIyMjIyMjQyOjIyMjIyMjIyMjIyMjIyMjIyNDIyMjIyMjIyMjIyMv/AABEIAMIBAwMBIgACEQEDEQH/xAAcAAABBQEBAQAAAAAAAAAAAAAFAAIDBAYHAQj/xABBEAABAgMGAgcGBAUDBAMAAAABAhEAAyEEBRIxQVFhcQYTIoGRobEyQsHR4fAUI1JiBxVygqIzsvEWJJLCQ1PS/8QAGgEAAgMBAQAAAAAAAAAAAAAAAwQBAgUABv/EADERAAICAQQABAQFBAMBAAAAAAECAAMRBBIhMRMiQVEUMmGBBXGRobEVM1LRQsHhI//aAAwDAQACEQMRAD8ApzVPFYoO8Triut4BHTPFSRsIrTbCDVOcWMRhuPjFgZQqIOVIKT2hSBV5WmZjIQk4cgY1GLeK82zJVEYUnJnebGBMmLNNXmTEsm6XNYPfhIiUhSXbPjFxj0gyD6y1c/4SWQkD83dfwh96XsoAhNTtvAyagKPaES2azkq7IJO8CdBnJMKjHGAOYyzXepfam1Jq0E5dlFAMoSrVLlj8xbn9Kc/pA219ISxEtIQN81eJiDaOlEnwz2xhQWdGp7otfhQMgD3UaM3YL0TM7K+yvwCuI48I01zWhzgVrkTvtCTK5fDzOsZs4MGW61S5SgCgORiyJzfi0VVdIGyH+0fAw/pdZgZ6KkflgMOavpFS7riE0qCT7IcufAUEFOxRkmP0WswCjkx0zpHMOvmfg0MXeiyl8R5VzEVJ13pwk4sLU58InsUsIDAOdzn9Inah5E46rbmOQqeupJSONPKHpsy3rNPd/wAxbGUNwaRJxE21Ln1jEWVSvZmL4xJLMxIIxvn5hsols3ZyBJLBhtvCUnOkU4PBnLqbF9ZCifPQOzgVwAb5R5/P5iCy0N3n4vFmUoA9ryiO2lBBdNPGKlkzgiGr1dgOO4kX8hR7UsHmEn0aCU632dSQMIT5ba+OcYdRY5EB6PtFxayxMXarkYM0EtyDkTUJsstVUTPQ/wC0/CGTLuP7Vf0lj4GMkic0XrPe81NAskbK7Q84t5x6ynkPpCqrIU0cj+qGKQdvCHSL+ekxAI/b/wDkuPSC1jlSJqSUrwK2y76/OJN+35hO8Hd8pgFSo8LGCU+ynMAqSCQ7PlxEVFSRsRyrBldW6gWQqZWwQol6j9wj2LZErzC5mHnHipgiMxGqOxLZjyrYwxUNaPCY7E7M9cx6FwwKMSODHYnRyFcY9UoERFgo5y407+X3WBlqvRnCC37vlt6wJnwcL3CKnGW6heTaJMsvMSFKPsg1Y8U7c/CBF7XmpyJfYQdE07n2gNMmk1PjE8uUuYyRQ7nKmvhFNpzuYyd3BCjEZZZMyarDLQpatkhz9IPI6F2tKOsXLajs7qA5CNT0GsQlWUrQe2uYQVihZLAAHNnjTTlFWGYCSSWfV8oBbqQuQsVbLcZnE7XYSHJ02ghcd8rT+WtiFEYVnNJByfY5RtemV1JWhc+WkBaT+YBQEEtiA0I1HfpHOkSsSmBSkl6qcCnxg1Vm9YqyEHBhK/rcTNIFSkYSeOZ73MHOhgIlTprZAsDrhS5/3CMykpXPOM9g5rFQS2Y5msba6pYlyRKGFlhOIcZiia/258oDqMBNpHeJpfhtZ8QsOgDM9ZrvmTFPhLvSjDOmcEP5GpPtKSOAcxslKlhQIw6vURVta0lRo70DHhnBcKoyDMtyXYmY+dIKCYfIwUcOdlfBs4fbgcRBfPXKBs5PE8GgO/zSgltJIJBAB4ZcIjUXJ5xAm2qKVY3KgOyaV4GkE+inVz8fWIHWCvBjTxibW2KXAyBD06drWwDiUZVSwrydoJyLBMLMIPrs8tFAkDIBhClWob7xl2axm8yiblOiSse5gCbZEqcKCCeYf1ioLgQVElRr7unjGzskhKllwDjTsM0/MHyiZdlA0EcfxIqcYhPh1PrMHP6NIPsqKfOAtpueYhJUGUkFqZ5tlHT1yEkUYH7bujPzLSlSTLwFKgoAkVFDXxh3Ta3xAc+kBZQFPEwoQoaEd0EJM4pQdCH9Y15s6D7LuxFUk58ID3hdSimgGbvUU2Y0d/WGDajjkiDUFDmDbvvSYgBlU2MF0WyXN9sYFfqT9t4+MBJ10TZYfDiG6a+UU0zCC2UWK85UyA/oZqv5ZM0WkjSh+UKAcu3KAFT4mFHbnk+SF8biI8ZhgRDgiHTFo8LeERDSISQd4rJkgEW59hMpIXNSQCHSk5nnsOESyJSZYE4q9mqcqq0Z/HzygNeN/qmlSZhxO9fvbeF7LGJ2r94ZEAGWg+87yKqaaAQLloUssA5OgiazWRc1eFAfc6ARtrh6PqFJaCpQ9pRAbx05ROVQShJY5ME3T0eAZUyqnDJGh074MXhdc+yFC1JTMQRiZQCwngpJFOY8RGosVxTEzJa8SCEpKiH98ZVq6R6xbXInEqMwYwcikhRHJqtwgBbdyTL1avwmwFBU9g+sDWHpulsMyUhIAb8sAgjkrLxggu8ZJGORMSXquSSxBr2kA58hDV9HJE0EqltuuX2TlqMiX3EZm8+jC5aj1MwTQPd9lY7sj3eECZcjmPJXodScKSh/aaezTkLTROJCgQcVMQUCksPGOSX3ZeqmzJZ9xRTzbI94aNBZ7XOlEpC1oIoUlw2tUn5RVtqBNmGZMGNZzJ1YNkGHlE0EVnnqXs/ALHxsYGDbnQVHEQ8tFTWmLQNuWbk8HZC1zCVZuXI5Agep8IdZJSVIIOPDVWFGEMRQPiFe7LvinKsMwl3VLWPZIo4ziz2KxyeIR9H8LpWRTlj2fpDKrJRJBCnzbSmVYq2teCg0DQ2Ui2J9kpUD7xQPhTyiGy3BNViVNXMZjhIWWBzJw6htogmrHzTzo0VmZD+IU7OacYiXOSRWnKkepsczEQF4gDqkHzb1hy7GuY4ACi9cILRGFHrCr+GXFS2OBB1pUxYZRc6M2nqp6TkD2VB9Ito6Kz1EYsAGyifgI1t09H7HLHbGJY95YBJ5A0A4esQ+rqRdvefQS1GmtVskYEsT09renlmIoqs6yThlnvoPONTZZclVELxUGn39mJJtlCSyhn7JrX72jGdbEywU4moLR16wDZkKBQVJIZQJqKDWLq5oJLEQ5aANIhmMYULbzmXyY1aBrA82FPWKVuxy1izNVh1pEiCDUH0gillGRJ3CIWUNl3fSKd5SFCWvDtlnlWL5UsZKfmPk0N69dQpKVJyLOC3mIlGYNnMG/mUiYcT9QWIq4iZZlTR+ahlaTEMD3jKKd42SamYUhC20LCo0yiAKWKFChvQ+EbyccgzB/wDrUcS9/wBLqNUzpZScnDFuMKJJRmAD2vD6Qov48N4zf4zzBDcMEDJBiIydo04SUlJi5d9kxl1UQHJJypU9wGfMDWGJkkqCdSWf48onve1JlI6scCr1Sn/3VxUBALnKjaOzCVoCcnoQR0kvTEcIokUA4ceJzPcIzsmUpZJGmvwiS0zTNmUqTBxKpdnlpBqpnbcnU7ARQeRcDuczZOT1DXQ+5+sUEkYUpGKYUu5H1LR0aWEgdWAEpGQGVGY8efGM1/DqaV2ZS37a5pCm0AyAjVLcGrekI3sQYvv3HPpPEoLV1pTPN4gUoYqGvCCcvqxLdVVNk5c5kDypAldpJDIYDKor4wKxQqgk9yynMltSwmWpejduj50CmGZo3eNozMu1IWshBVpkyebgCvcY0E9BTImqLECWvPcimW3wjCWKQqkwqwIFcZLAb18YYrLOoJlSMHiWumtqly0SlrY+3zUADhD5s/rGY6Oy5lsW2EIRiwuASSTXCBuBUnQQr3nfzC1e0UyJSXJAySKEgZYlFgOb6Rp+jU+VJmINEJQlRRLdycXYFdVFSnJ4QSwrWv1P7Ca2gbVbC6sdo9Pcyva0iVNlISCAVBDFL9mta5s3rHi7c4UkpMxUtWEKbApQ4oFfIZ+JW2zlKtiZeHGrqytsNU4mSpi2TZvm4aIL0sQSoTDVavZCQBhDkAE78DCeMgZHGM5+8dGqBbzc+kG/zdCCAAo0Dksf8XDeMMXb50whCAsg5gEB+5IdvKL8no2V/mLUCdaRak3YuWpwcQZsOQZ9OMC8elTx3C+LUvKrz9YGRdqiuWicohJc4EM4bRxQefnGysdjlJThlgBgzEVHj9mM1brQtKwtYKCFADlUsCOX28aC5x1oCvfQxIcuQcqg1yL55CDvpPiEBDfp1FL9Q5PPUbagQ+cDsCiaO3Krd8aCepK1GWGKsL8wXDPk/CBf4YozL02EZTVtSSrDn+ZZLAwktjRgWJiSymILag6EfeUaGROTNQdxoRX/AIjLrnYYo3hb1IDpCiDQ4SQUncN4eEPaPUt/bfkHqLXVAncO4ftpAUau0D1WpP2wgRPvlMuWlVTicBy5DM4Lah684DrvZJq8LDRNk8cS4IxzNJabYghqvAez3wETcCiwLFJ50aBi7yGb+cBrdascxyGSAwIzGr8uEPUaMHIYcQb2Bep0pFpL8IcbRGTuy8lGUAouUdk8dvKJLbeBUwf/AJhU6PDFZ2+aWZMersUv6fdImkdWvQAjTLfxB3jEqtZdsZCfUjMCD3R5EyYt1A9XhIdVD+3viX0hC8SQw7MO9R+2FF6XJoO2fKFCfw93sZPir7zKGXDVIifENTDcR5x6+Z0t3XLlIRMmzXJFEJAURo5LDUkBn1MYnpNOxvMSXS9Q71UScT6gmnBgI1HSa09VLCE5pSVFv1HspP8A5FSuaI5uu0Vw5jb19AYXQF23QrkImJdsM8Sk4wAZiqh8kjQ8+EVpk1SyVEkk5kwxI1UYapT5ZQXHMy7LC35TefwwvtEtcyzTFACYykElgVChT3jKOmTQxIap3H28fOwlEmlCKvtG0uT+IMyUEy7SgzkJolbtMAprqOcL30b+V7k1uBwZ1dQCUqepxd7sfm0RIlYyQkBzo1ef3nGVR/ESylP+had3CXc8SBAK/v4nLwqRZZSpRVQzF+13DKAjTOxAxxD7wBND086Qos8kWVCgZ0wgqArgQC9dHOTcY5mZtptKsClOaskUSG1OwEDEyJ84mYEzJiiaqCVKc8wDG5uy57SuWjFJMtJrMohBIGXvFRJ4gQdymnQcj7mNaSqu84c459ux7SnIkJly8CFApBCpiqutYyAH6RoOZie6krVaJUwg4QuqmJSAkY8Oz0YcSIIJuBRU6ykIBohCnccVN9eUay4LIpS0pwBMtIcgBqaDvPxjPbUBmAXkmehvtrpo2VdARlzIxqTaFoWlUxISyvdBdaQdncUzyGke3/ZMScaRVGgzIq/eNOcaz8MhlhvaU55xn5i3URBb02IAJi1WFm3e0GXLa5KksTnq9eXP6wWRZcYBBcDZsqbRh7fJmWa2oWh0y5gUzOwUWxBmbYudzGzuO2YVdX2llKe0aHXgc9Wi50VVtYwMH3ktYwJIla23claClaaH77uYgZY5XUqIVMW5KUy6PoWBIGblg9KCNhbUJbEPZUARoxPzgHbpTPGRXe+jtKHkQyN4iyW7rUVTFO5oA2TMHduINeUXbfYiU4iK68t4zP4hctQXidiGoK1Dg9wzjWWC8UrZCsyHD8fgY1nRNXXwfy+hgrN1bAiYe3qKVfCIZk1KklJNCI0HSO7GLpFPukc/6QKXLSSks5Af9L0eM+irc4Q8EGMtYuzdH2eWFFFnmAKRMUcJFClYyL+XEGBU26SFEBbgEsRrDblCpjoWeyku+r8OHGNXZ7kVRTBKdOMaVtvgHBP+osF8TnEy6rjJDqJETi55hGH3QM1M8b2XdqDVTqGgyD8mESosUtKSGfWpMJN+JkcS/gL6zntnuC0ANiCcdQ9eQLZOIkR0UtClMqYySxJDnyLesdDQlKUlgBES1jC3nA/6pYTwB+k7wU+sxlg6GlCytU0qYnCAGc8fpGksy5ksEpwjRNHpzNSImUpw2giNZCuQyiG1lrnLH9pYVIowBJE22YcyO5P1hRB1nKFEfE2e8jw09pOtCFOcuDfKG3fZj1o1SkFRb9tR3uwhTEOHA++EELkQGWvTsB+8qI/xEehsOFMQQciYLppaSZig7ssh/wCjsnuxlZ74ykiX7xy04wTv2diWl/eAJ/ueYfWB61vEVjCYEBqny2IyaXgjd90TJiQtsEpyOsU+EkZtu3Cm5iz0SuYWq0YVv1UtJmzSKHAlgEg6FSiE8iTpGhvy04wEpQEJR2UpT7IADMkaARLNjAlKKN/J6lSTZbNJHaQZppVZYbHsg1GzvBKX0jlSwRLkJSdkslPeEgE+MZ1UpWpqPsehh8qyFSsIxMD2iWLMW+Fe+Bbcx4KidCE7T0rmA9kJA2ard9Yd/wBWzVAuhB8eWpihPu0KJCS7FnpXiAz93CJzdP5gwy6OxGIYCQM3GQfSO2LLZ+kK3Z0wKDhV7G1GHDgI0UjpBKmI2JoW8HjEL6MzcBmlUsJqe0oJzcsHHhXQPFGy4gopFGNWOxHzhW7Q1W5YdwiP7ibK329IqN42XRpX/bhZzW57nYennGMl3ZKtEtknDNGo4Ppq5z1742lyFKZaEE+wEpI2KUhz97wtVStTj7y+pfcmJdtc0pSa5wKkyqKVuYsXlNBNOMQoWyfKOvu5IPQg61wsH39YRNllLOodpGhCwCzEZOHT3wN6P2vqxJWSrDNGFVGwqJoW2BDV/VB/E8YLpJeKbPaVImuJa0BSKEghzkw7JBBB3Cov+G3FiU+8uxVR5p1iUBMQ7UNeR+BfzBgRbpTiudabNAPoX0wlTlGWFurPCQQSN6mpGW+RrUxpLxTqB7Xr9R8Ip+MacbRYOxAUnD4B4mUtCRURFd/SQSZiZM9AWj3Fv20jlmR8tYltwZVYyvSKxdZhUl8SSWUCzAwD8Os2tyeDHbRuTqdUmzZc+X2VO4JFM6Rz6+rEDiSRTYxD0Y6RrlKTLnBnIAXViTQci+fjGsv2ygsoD2qnnrDWrTY4tT7/AO4KnCnaejOX3LMEuepKg5ChptpyjpkxbJxAHCW02HHT5RmVWtUlbAZ1dnb5Rcl25UyilEuKvCurbxiGxx+cOte0YzChnhOldvrHksE0S53w1NeA9YzM0rKwhLqOIBgHU4qG8AeTx0G6rOZcmWlXtEEqyoVF2PEO3dCt1YqTd7yjviApqFJbECl8gRnFdcwb/KNHeVnExBALHQjT6RnrzsglI6xJdLhJScwojP74QOjFnXc4OPWVCsgZmuY8/CGKS4Z6+EME0H7zBiZKXHJyC+XAQz1LmSJCOPgPnCjyn20KKZkSbrARn5QSuqcDLUlLBROxUSwYAJHNySdRzgCsF6GDHRon80jNSUsOKRML/e0eluGVmah5nLukc/rJuOmIZsMLsGFNKMPCK922BU5RY4UpDqURkNgNVHQRL0ollNqmpGWJQ7gWB8IP3FZv+wUoFnnKCqZ4UI1bKuXOJztrBg2QPdgzy77QmWiZKkggLbEpRBK2ycjIVNBQV5xKJeMPV2c+p+EUrNLqwbUfN+UHLGhKuwPZ4O6jm/0hSy3aNxjqV58qjEppAfsgmhGVGIYhsqiLlnsK1UZg+oz+kGZFjloAYZcBSG2+YkIUMTUYAZqUckjg1SdBxIhD4l7H2rHUoUYzKokoT7SwDnmkNr8DF6UANKOPd17hA6WVzAHl4ACODJGjDlmattBVShmAfXm5p4QG2xl43cwrVqvElmISoALGdKmnh95R6iwSa0CSfeA8yNYhemzw9KyNYrXrLEPcE1KtBV7WWbZ1oWPYxdlSci2nnUGC953kUS5VsQezMZEwbKqAe4huSxsItpCZstcpbgKqmjhKhVJ+B3BMZy8gf5RawqgSUKTuFY5fHgI1kZL1B9/2MRsBTv0/iFxeQKQXzi3ZJ2Id8cpua+FgJSskpFArbgfnHQbttbAMIzdXS9WQeYVCHGRNCEHhnGU/iBdqZslMxnMo1OuFZCT54T4xpLPav2mHWyzibLXLIIStKknvDO+4zhOm4U2Kw+8rahdCDOJ2GyL61JlOFpUCkjQiojtN2X9LmfkzCJdoZLyycyzjCclU0zoKb4DomhMmfNXOYGSF4h+5NAz5g6a1g90TugTVqtk52xEywTUqzxPsmgHHlG/qW3oQ3REzqFcOc+kvXoir7wEnyyXEam9E4sgc38YBzkCoFSNBGDUSvHtNtDkQdMu4zE0lk5uw2gp0atU1UqYiYoqEteFJVUs2+rVHhFW1WxcmUsD2pjJQ/wCo08Gr3QbuixmVZ0pX7ZAKmDBzoBsAAO6GbbWWk56JwP8AuDIG/Ez84YphVlo0WrPLGIMIvTLKmpOsRplhMLm7IwIwRnqGej8hHWLICQoYASwchQoHbKho+kE7SpzA2604EFWqzi7gAB6P3xIucX+G+8KXPv8AKP1ifhncTJ2AFP8AiA17STNSUAgf1O1G8+MWV2mrPFGYtRJWGYbxbTDY4Yy7ISIKk3HaFUSlOz4wOfGHT7ntqKCTiDE9laAPEnODtntocg0I2yi6u9U4PaBpSsbQenbknJgS1gOMTnk27Lwc+7wAFPIwo2RvNAzrxY1hQD4w/wCA/QSdpgd9ovdH5uGa5oCP9pCifAKEVSp/seEeybTgWlTVSQfOoj0LDIIiUyX8QLvwWs7KSCP7ewfMEwT/AIetOslpke9LWJo07K0hJf8AuSPGDH8RbsK5KJqQ5QCH1UggAHyCj/UY570Qvw2O1onFzLqiYBqhWfhQjlFEG+srKOxRw0OyEqSVaEOO80+EHrnQACWy4AgnlFrpLcwBFpknrJcwYnTVwauw7oiu4jCPusZOsJC8zW05DciEPLgaQsnoKhnAq2z7R4Utpo3GPAnfujKDeojgEZMs9oWo9SEhCQSrEUgnk+nmX2iVBVULGFQLEbajuILjnEU1RQtMyWAZgHsq9lQ2UN9joTFCxT5kyfMUpBS4GKlMSSQBzbyAhsollWVHInAMc9YH6wkoqdmpvHqOJjzGNCCYeZb5vzBhTBPpOziSpnthLZEZDNqxnumtoCLGizA9qetKiNkIOL1Ag3PtMqTK/ETy0sVSk+1MUKYQNnzPGOWW2+1Wi0qnTaYqJGiA9BG3oKHVd33/ANTL1VyE7YYsllBSA1NvhBm65E1DBHaR+kkAjkfh6RRsSwQCC/ERpLsI1Hr9vC2ptYZBGYatRjiELLaecE5UyKYCTox3h1mJdmPcIxLFDdQpAxM90m6PGbPRMScKZjJW36k+ye8U/tjWWWyKSlCS6QkBmZsh8Ik6nGMJHJ9xlDvwaCgElidIaa2xq1GeAIA7RIJ1gSVBIU5X72wAffP6xP8AyWXLTSurlor2lGBKAklwoMdXeFf95uhMpD45ntN7qcj45CD6Ui1SccyG3DAB4gGxWX8RalTVD8uVRCdCdzxceA4wYts8AZ1i3KsyZElMtLAj2uZzrwoO6BcxKc1HwgersDMFHQhaufNK6pjt98IdZ5eNYTvnyGceBIJeLt2JHbXt2B5FXwhckAEiHZsLLq6UGQy4RSnLbWPZ9prEQmAu7vU8Cw8oCiH1g8YlZZKlEOwGmcKzqCpKFfqBX3KJKf8AFvCB15WlUuXMWk9oIU39WSR4keMWryM2XhQhKChKEh1sACAEgJA5Q8KsoPqf47/mVLYbEq2hZJUQc++BM6eUUCjSFabZhWxBSqtRkR4wGtVvUSdWqXbLxh6nTsZD2AS3NvNTnteUKAf4hRrhNYUPfDD2gPHE6C9TQOd6RHhzpSlOPwiaZEChTP104mH8xPEJWNKrVLMorKZSeyvAe0pZALPVkCtAzkp0jkV8WRUqYuWsMpCiDxO/IhiOcdO6MWrqrRMlmiJmEjgagHuIb+6K/wDEu4OsQLVLAxyw0wDVAyPNJ/xP7YDU21sGdem5cj85mOh3TSZZPyZgMyznNOqd1J4cI6LY0Wa0MuzTAHD4FZHlqI4gpLU11+UXLLapkogoJpUgFuZ4Re6hbRzAU6lqzOzTbKtJZSTlQ5jxER7Rgrq6eTUMOsIbRdRxrGgldOgoDHKlK4v8Yx7fwwg8TUTXAjmE7ZaVoBUhPaAGHsqUSS9AEijbkjMZxSsygSJk8KUolz1iMSH4Idm5iIVdPJA/+GWP7j6P9tFC2fxGA/05ctJbRIJfmfnBk0bhQAPuIT+oIFIxNJNs6pziTICUZ0lploFM8bhu58zFW3XxZrEgJmTOvmAPgB7L8TmptqDnHPr16a2ue4VMIHCM8SVFySTuYcr0QHLxG3XsV2rwIY6Q9IJtsmY5hLCiUjJI2AFIoYKN4wyQjWLCEeGsNnA4EzWYscmKy2qZKLoUz5pNQe74xrbl6TIJSmYOrV/ie/TkYz8+zsUuKYEq5uT8XHIRXNnxEk/Zhe6uu0YYfeGruelsAzsNlmBbHICpP12ghdkhJV+IV7SkgS391G+XtK9OZjlXRSzTFzUy+tWJPtTEBRwqSPdbJiWB4Ex0m0XiTy04Z0jB1NPw7YQ5J/YTVpsbUDOMCG5l5BLuQYEzLyrTKKYUVZ0+/WG4EmEWyx88cSpFk67wACpkwshCSo/e8DujExc1SrRNfEpQWB+lIcIT3B1d4jP9KremkjEwUtKVM5Nc+yHJYOOao2qbOJctKci1Rsdu6g7oeYfD6YY4LdflAbhZYVHpPbbaiYH9WdT9/KJ1M1CGEV1zUjWEVXAwIcL6CSHspJ4RdshwSE7qBWf7i48mgWpJmYED31JSf6c1f4gwVtqnNO4cBHOMKAfU/wATiMtiRKU+mkRLJD7jhlwheucVJ83jXWORczscyra5eNcmX+uYkq/pl/mK7uyBFu02383CtmNKjiCDEd2AG0Yv/rlE961N6JPjA6+FOT5Q4BuZU9h+5/8AMSmOSZW6Q2bCddwrLOpgSm45yiRhIG5cEjcgD6x6u+F40oVqoJrUZiNQJ5LBQrsKd7Rsaat0XDRO0gmZeZ0etDn8wf5fKFGuFqlbLPgPhChncYDAjliIpgHzpVotzEZ7cfvOKy+XdF50qLljEDqAwyyLOPQ90am7LaJiChRdQABcZuNvEHkYziwKU+cQotSpawpJ1rx4QO1cjMuh9Jl+mXRz8LM6yWD1Kz2f2K1Qf/XhTMQAUphg/wDL5R2lSpdplEKAUlQZQP3Qgsx0LcI5jf8A0ZmWRbqdUonsr13wqGio6u7PDd/zE9Tp9vmXqZ5dk7OLfL5xV6qNFJRiDkchDzdBIxN2jly3+XjFxqAODEg/pM0ZdWhyJL8oPpuNWQ7yfvKIJ9gUKJFPWLfEKeAZPiQRgegESpkE0EFBYSmhz14cIeqzYACRUinKINw9J26UsGFsiK/IxIEgoce1V0/EHbhDzaFoHZUBhehSgg8wRXvir1y5hqlKiK9lIS3ekBhziwG4RiraeDC17WoKVLbLqwAP7llvOK5UmiQCo6sQAD4FzFe3WwLIbJIZIAYhNTnrUwyy2gA5HmB6xUpgZhbcZJAhixW9UpRwMSzF9dTX7ygzZb9SVMsFJ45eMZySh69/OL9glO6lBwkORudB4/GEraq27HMHVqnq4WbGXakqAYvHtqtPVoKn3bwjHypigSyikhiQH1dqZZgxJbLTMmAErYBhQbu8I/Becc8TQ/qB24IwY/onYzaLamasdmX+at6s1JaeeKv9pjb260lSuA+9YyNwXkmRKWgpOJagcQqMKUsBvSp74ll3wlZ9od9PWJ1VT22Zx5VGBL6O6tVyTyYWnlSqkwyXIc7xWl23F2X8PvOCkspSgq2zhZlZeI346+8kuskLmKGUtKUg/uU5V3gBP/lDptuL+pixddnCJWeIrUqYo74mZuGECKk8Jz0hdiGsPHXH6SUtGMmQrtTxEtZNYlMyWkKfNuzXV/lAa1X1KS/a7hU+EMV0s3yiVe5V7hS5J4KrUrbq0+CVH4+cCrwnBzFLo7aFgWidM7KJhThB1Ieo4AMH17oEW+8ytRTLD7nQd8PrpT4xx6Y/gQC6hdmT65le+FiheoyixJvZYCVFMwBQZJIPaYAFjrplFi7LjAAn2oun3UP7erl8k8NfWrfl4jGoJHbUA6iGwpYMhIFEpA8cztGogAAUcxNy3zHgR/8AO219YUZvDCgvhiLeLOymZiFC4+9e4w0/b7/KAV2z8FomyleyoqUOBGfiPSDZVmx2y+3gKncAY2wwcRi6/f3xirNTT75CLBV3/f0iFY41z+2i2JEhsVuVJUSCzDzyBbf/AIg6q8E2iXgUlK5qwXxBxLl0rh3y73JLARmbfIdJbMDxOggTdt7TJc9RBzDFJ1YnwNc4Ueogn2jKsrLz3Cdu6NrkLxoOOVpungrh+4eUTyJeqix1O30jR3deCJodCv6kKoR8vQ8MoHXvdBWHlFmzlmgPyPA05QByWPmmfqNET5k/SA500KLJon1+nCEtSUCjFRy4cYrz14HCwQRTCaF4ppmEniYtsAHEzdpHBl1EtLFSvZFSfvWBilmYorNHyGw0Ee2u04mlpNBUnc/IQ6QMIxa5JHGCKu0ZkynbJIfAOZPH6RBeEwpT1SOygsVH3l7FR0GoTyeuRWySA5WqoTU8ToO8xHOsSpgUoipqeA4ekGS0KcGFSzaYAnqq42YDgKCLVlnDCAd6/CJ03YpRPZoM+AhSrAoqLAgbaDjBWsQjGZY27u5Mm0AJYanOLyZxCSlLKq7pOYGo3DxXk2BSlAAcI9nKCVJ7RSH2GQzILg5PpAAqucCXqQOcTyVOHWzKe0UjgEhJIP3vE862yyAhOQLqO5ZkgeJ8RAy1rAUpQoFZEOqgpwrQQrHbJUvckZkir8oK1eRnEPehzke2IUl48KiQwPZTvX6A+MNVZMKTo7AAGjnhyeJrNaErSkoyDkuGqfp6xZtJfAGyGI8z9B5wtyDjqZ5yDBEtBTkpuVMuIi8qaoJwFZwlit31qA2pplEkuzJJ1p8M4o/jUEqWkuasDts+7RIUseYxp1Z25PE0Vt6QKUgCWnCkJDZ0AFMuEB51smgjt1Z6cctIFy7QuXiNcKgGS4yJc8qDzieTa+tmE4KakKLJYUDtWOGlVBwBGLw6gYPXcnnTZi6FfCKk+xh2d2p4Z+cGbNhFSPZBV4B4qSpZVzJ5kk6AamIBK9RMNZYcdykqTMWjC6lZBKRXuA5PB+xWeXJlgTUIOE4gEB1FRSzLUXB5MwZy7xC6ZXZHaWdqgOBR8n5eesaVqUGIo78zxMEyQvM0dPUy8tPbXPVNmFa6fpSMkjQcTxirabAiY2IGmoLUi0EQ1asgO4QMOQeO42VBGCOIyVd1nYfl+phRTXeEgEgzVOM8KXHcXjyCbbfrA76fYQl0qsq5a+sDtuN8vSH3LfJUwUSdPSNvIs0q2SilQHaDcIx1p6ITZUx5RC06AllDxoedIvW4A2mWsRicrDHWvrnsW+sMxcEkDaPLPY5oSxRXmC3Ghic2ZYzSa1OrfWCbh7wW1vaRKR4fejQDvS7sSusTRQFRvB5ZpseOffECkRPcnkTL2W1KlqxJJSofbRrbs6RpWyZgwqyChr3n0PcRGcvW7i+NGeo3gSmboaEbwu9UIlnvOl22xS56WWAWyWKKS/p30PGMrfFxTZb9WMacsQFUjJm34xRsN9TJbMXA0f0OnKNXdPSCVMICzhOpyLa0yPd4QHDLyJ1lNdo57mKkWVhyzixLlFR9BHRrdJs0xBWsJKQCzZhgDpV6gcSTtGdnXCrDilqSXzD5DZ9+bQM2MexM+zROvK8wSmW/ZGQqeJy8otGXkgczz0HdEibKuWHKSDpqObiPFLwB81Hy484FnMSZWBwRiNnMkYBXVXwHxhqgEjDqrPl9fhEaTQqJ4sTUmH2eWpRBPvVB5UPg0XHUrIbdMXKl/lpdS3SFdns7lialu70jGzVKxsoZHtF3Ktaq1jYT53WLDeymifie+KdpuhCiUpNdTuduQhqm0JwRD13bOIDM8KlJrUKWO8nGk+ah3xFZ7QpRbWDKrmMvLCSoVBDhtDzGYMVE2MyyXGX2IObUYcQr3BwI+VPUFUMXZdqxKJP20OsljSUlZ0FOZyh0ixYa5vCzOIAsJbkUlqUdmfbFSM5bp0pX+nLyzUacKtpz8I1VoltLQke8XPIU9T5QJvKxuEGpZTlI1asTRYA3MJTaE4x36+0GCW5MtRCVYHyHtZ4eQFG5wyzXmUDAUjZho0VZy1lZWQxd2PEvHqVoK8a0nDqlJr4+rQ6QD3G3dHGJoLNbewVKTQhgHz+kErJeIRJKkywZs1wJhIaWkFsKU6HXvEUfwKlzMFAAxOzadzQrxvaXKmJkhPYSDjIDkLLeLZNCe3ccKIzWiUrmSyAVTMay6jmcu+kSrQEkj7aJkWcKSFS3KSAX51rqDUUMRKQo9lIfybv29IG2QYcEESCYqAN43me1LlkMaKWNd0pP6eOvKFeluKlFEs9kZqHvcv2+sTXX0bmzBiUnq0fqXTwGZhpEVBubuJW2s52pBHUx7G5l9G5LB+uVxCaHlCiPiB9YL4az6Qx/C9ZMsOSanXiI0NrzXzPxhQoHd800aflmHtU1TntHXU7xHJWSoOSa78oUKLS/rNKKpU9aDOsVF5DkPSFCi6dQFvcqWnXujK34O0IUKCCBMpoiey59x9IUKAP6wtfpDdkmqCZbKI7W5/XG4tn+ihXvMO1r45woUKv1GT3Il+0O6Jek9nQEOEJBrVg+Z1hQoG0X1XyTD2rNPKJ8R6tVfcX6iFCiw6mGfWVrDnFiw/OPYUcfmMoYp/8Aqq5/CPL09iXzPwhQohe5C9xL/wBNH9XwiQZp5fEwoURLGS2z2kf0D1MV7XknkY9hRC9zjA97pGAU1ipKQMOQhQodT5JdeppLkWcOZ/00a/tXGOu+sytawoUXr6aaGq+RZf6QLKSSklJxZihz3EajpNSwLIoSpAJ1I2J1j2FHH/h+cjS/I35Sl0Fs6CAShJO7B/GNBYe1MVi7TZPVvGPYUBs/uGOVfIJg7ytkzrV/mL9o+8fnChQoZkT/2Q==",
            4.23,
            2500000l
        )).collect(Collectors.toList());
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("totalPage", postList.getTotalPages());
        return result;
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
        Post post = postRepository.getReferenceById(id);
        stepToCookRepository.deleteAllByPost(post);
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
        List<StepToCook> steps = stepToCookRepository.findAllByPost_Id(id);
        List<IngredientOfDish> ingredients = ingredientOfDishRepository.findAllByPosts(id);
        return new PostsDetailResponse(
            postsDetail.getId(),
            postsDetail.getTitle(),
            dishTypes.stream().map(dt -> dt.getName()).collect(Collectors.toList()),
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
}
