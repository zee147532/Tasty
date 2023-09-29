import {Link} from 'react-router-dom'
import {AiFillStar} from 'react-icons/ai'
import './index.css'

const PostsCard = props => {
  const {posts} = props
  const {imageUrl, name, cuisine, rating, totalReviews, id} = posts

  return (
    <li className="restaurant-item">
      <Link to={`/posts/${id}`} className="link-item">
        <div className="image-div">
          <img src={imageUrl === "" ? "https://icon-library.com/images/meat-icon-png/meat-icon-png-11.jpg" : imageUrl} alt="restaurant" className="restaurant-image" />
        </div>
        <div>
          <h1 className="restaurant-name">{name}</h1>
          <div className="rating-container">
            <AiFillStar className="star" />
            <p className="rating">{rating}</p>
            <p className="reviews">({totalReviews} lượt đánh giá)</p>
          </div>
        </div>
      </Link>
    </li>
  )
}

export default PostsCard
