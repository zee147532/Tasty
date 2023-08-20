import {Component} from 'react'
import Cookies from 'js-cookie'
import Loader from 'react-loader-spinner'
import {AiFillStar} from 'react-icons/ai'

import Header from '../Header'
import Footer from '../Footer'
import AllStepEdit from "../AllStepEdit";
import IngredientListEdit from "../IngredientListEdit";

import './index.css'

const apiStatusConstants = {
  initial: 'INITIAL',
  success: 'SUCCESS',
  failure: 'FAILURE',
  inProgress: 'IN_PROGRESS',
}

class RestaurantDetails extends Component {
  state = {
    restaurantData: {},
    foodItemData: [],
    apiStatus: apiStatusConstants.initial,
    allStep: [{index: 1, content: 'them chao vao dauthem chao vao dauthem chao vao dauthem chao vao dauthem chao vao dauthem chao vao dauthem chao vao dauthem chao vao dauthem chao vao dauthem chao vao dau'}],
    stepInsert: '',
    index: 0,
  }

  componentDidMount() {
    this.getRestaurantData()
  }

  getFormattedData = data => ({
    id: data.id,
    imageUrl: data.image_url,
    rating: data.rating,
    name: data.name,
    reviewsCount: data.reviews_count,
    location: data.location,
    costForTwo: data.cost_for_two,
    cuisine: data.cuisine,
  })

  getFoodItemFormattedData = data => ({
    imageUrl: data.image_url,
    name: data.name,
    cost: data.cost,
    rating: data.rating,
    id: data.id,
  })

  getRestaurantData = async () => {
    const {match} = this.props
    const {params} = match
    const {id} = params

    this.setState({
      apiStatus: apiStatusConstants.inProgress,
    })
    const jwtToken = Cookies.get('jwt_token')
    const apiUrl = `https://apis.ccbp.in/restaurants-list/${id}`
    const options = {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
      method: 'GET',
    }
    const response = await fetch(apiUrl, options)
    if (response.ok) {
      const fetchedData = await response.json()
      const updatedData = this.getFormattedData(fetchedData)
      const updatedFoodItemData = fetchedData.food_items.map(eachItem =>
        this.getFoodItemFormattedData(eachItem),
      )
      this.setState({
        restaurantData: updatedData,
        foodItemData: updatedFoodItemData,
        apiStatus: apiStatusConstants.success,
      })
    } else {
      this.setState({
        apiStatus: apiStatusConstants.failure,
      })
    }
  }

  renderRestaurantDetailsView = () => {
    const {restaurantData, allStep} = this.state
    const
      imageUrl = 'data:image/jpeg;base64,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',
      rating = 4.3,
      name = 'Fried Chicket',
      location = 'There',
      reviewsCount = 2500000,
      costForTwo = 200,
      cuisine = 'abcdefg'

    return (
      <>
        <div className="specific-restaurant-details-container">
          <div className="restaurant-banner-container">
            <div className="banner-responsive-container">
              <img
                src={imageUrl}
                alt="restaurant"
                className="specific-restaurant-image"
              />
              <div className="banner-details-container">
                <h1 className="specific-restaurant-name">{name}</h1>
                <p className="specific-restaurant-cuisine">{cuisine}</p>
                <p className="specific-restaurant-location">{location}</p>
                <div className="rating-cost-container">
                  <div className="specific-restaurant-rating-container">
                    <div className="rating-container">
                      <AiFillStar className="restaurant-details-star" />
                      <p className="specific-restaurant-rating">{rating}</p>
                    </div>
                    <p className="specific-restaurant-reviews">
                      {reviewsCount}+ Ratings
                    </p>
                  </div>
                  <hr className="line" />
                  <div className="cost-container">
                    <p className="specific-restaurant-cost">{costForTwo}</p>
                    <p className="specific-restaurant-cost-text">
                      Cost for two
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="step-body">
            <AllStepEdit />
            <IngredientListEdit />
          </div>
        </div>
        <Footer />
      </>
    )
  }

  insert = () => {
    const {stepInsert, index, allStep} = this.state
    const newStep = {index: index + 1, content: stepInsert}
    const preArray = allStep
    this.setState({allStep: [...allStep, newStep], index: index + 1 })
  }

  insertStep = event => {
    this.setState({stepInsert: event.target.value})
  }

  renderLoadingView = () => (
    <div className="restaurant-loader-container">
      <Loader type="Oval" color="#F7931E" height="50" width="50" />
    </div>
  )

  renderFailureView = () => (
    <div className="restaurant-error-view-container">
      <img
        src="https://res.cloudinary.com/nsp/image/upload/v1635664104/tastyKitchens/error_1x_csgpog.png"
        alt="restaurants failure"
        className="restaurant-failure-img"
      />
      <h1 className="restaurant-failure-heading-text">Page Not Found</h1>
      <p className="restaurant-failure-description">
        we are sorry, the page you requested could not be found Please go back
        to the homepage
      </p>
      <button className="error-button" type="button">
        Home Page
      </button>
    </div>
  )

  renderRestaurantDetails = () => {
    const {apiStatus} = this.state

    switch (apiStatus) {
      case apiStatusConstants.success:
        return this.renderRestaurantDetailsView()
      case apiStatusConstants.failure:
        return this.renderRestaurantDetailsView()
        // return this.renderFailureView()
      case apiStatusConstants.inProgress:
        return this.renderRestaurantDetailsView()
        // return this.renderLoadingView()
      default:
        return null
    }
  }

  render() {
    return (
      <>
        <Header />
        <div className="Restaurant-details-container">
          {this.renderRestaurantDetails()}
        </div>
      </>
    )
  }
}

export default RestaurantDetails
