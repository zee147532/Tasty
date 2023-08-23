import {Component} from 'react'
import Cookies from 'js-cookie'
import Loader from 'react-loader-spinner'
import {AiFillStar} from 'react-icons/ai'

import Header from '../Header'
import Footer from '../Footer'
import AllStepEdit from "../AllStepEdit";
import IngredientListEdit from "../IngredientListEdit";
import Comment from '../Comment'
import Rating from '../Rating'

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
    apiStatus: apiStatusConstants.initial,
    allSteps: [],
    allIngredients: [],
    stepInsert: '',
    index: 0,
    editable: true,
  }

  componentDidMount() {
    this.getRestaurantData()
  }

  deleteIngredient = id => {
    const {allIngredients} = this.state
    let index;
    for (let x = 0; x < allIngredients.length; x++) {
      if (allIngredients[x].id === id) {
        index = x
        break
      }
    }
    allIngredients.splice(index, 1)
    this.setState({allIngredients: [...allIngredients]})
  }

  addIngredient = item => {
    const {allIngredients} = this.state
    allIngredients.push(item)
    this.setState({allIngredients})
  }

  clearAllIngredients = () => {
    this.setState({allIngredients: []})
  }

  deleteStep = id => {
    const {allSteps} = this.state
    let index;
    for (let x = 0; x < allSteps.length; x++) {
      if (allSteps[x].id === id) {
        index = x
        break
      }
    }
    allSteps.splice(index, 1)
    this.setState({allSteps: [...allSteps]})
  }

  addStep = item => {
    const {allSteps} = this.state
    allSteps.push(item)
  }

  updateStep = edit => {
    const {allSteps} = this.state
    for (let x = 0; x < allSteps.length; x++) {
      if (allSteps[x].id === edit.id) {
        allSteps[x].content = edit.value;
        break;
      }
    }
    this.setState({allSteps})
  }

  clearAllSteps = () => {
    this.setState({allSteps: []})
  }

  getFormattedData = data => ({
    id: data.id,
    imageUrl: data.imageUrl,
    rating: data.rating,
    title: data.title,
    totalReviews: data.totalReviews,
    description: data.description,
    costForTwo: 200,
    tags: data.tags,
  })

  getRestaurantData = async () => {
    const {match} = this.props
    const {params} = match
    const {id} = params

    if (id == 'new') {
      this.setState({
        apiStatus: apiStatusConstants.success,
      })
      return
    }

    this.setState({
      apiStatus: apiStatusConstants.inProgress,
    })
    const jwtToken = Cookies.get('jwt_token')
    const apiUrl = `http://localhost:8080/api/customer/posts/${id}`
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
      this.setState({
        restaurantData: updatedData,
        allSteps: fetchedData.steps,
        apiStatus: apiStatusConstants.success,
        allIngredients: fetchedData.ingredients,
      })
    } else {
      this.setState({
        apiStatus: apiStatusConstants.failure,
      })
    }
  }

  renderRestaurantDetailsView = () => {
    const {restaurantData, allSteps, allIngredients, editable} = this.state

    return (
      <>
        <div className="specific-restaurant-details-container">
          <div className="restaurant-banner-container">
            <div className="banner-responsive-container">
              <img
                src={restaurantData.imageUrl}
                alt="restaurant"
                className="specific-restaurant-image"
              />
              <div className="banner-details-container">
                <h1 className="specific-restaurant-name">{restaurantData.title}</h1>
                <div className="specific-restaurant-cuisine">
                  {restaurantData.tags?.map(tag => {
                    <p>{tag}</p>
                  })}
                </div>
                <p className="specific-restaurant-location">{restaurantData.description}</p>
                <div className="rating-cost-container">
                  <div className="specific-restaurant-rating-container">
                    <div className="rating-container">
                      <AiFillStar className="restaurant-details-star" />
                      <p className="specific-restaurant-rating">{restaurantData.rating}</p>
                    </div>
                    <p className="specific-restaurant-reviews">
                      {restaurantData.totalReviews}+ Ratings
                    </p>
                  </div>
                  <hr className="line" />
                  <div className="cost-container">
                    <p className="specific-restaurant-cost">{restaurantData.costForTwo}</p>
                    <p className="specific-restaurant-cost-text">
                      Cost for two
                    </p>
                  </div>
                </div>
              </div>
            </div>
            <div className="update-action">
              <button className="action-button">Chỉnh sửa <span className="material-symbols-rounded edit-icon">edit_square</span></button>
              <button className="action-button">Lưu <span className="material-symbols-rounded edit-icon">upgrade</span></button>
            </div>
          </div>
          <div className="block-body">
            <AllStepEdit steps={allSteps}
                         onDelete={this.deleteStep}
                         onAdd={this.addStep}
                         onUpdate={this.updateStep}
                         clearAll={this.clearAllSteps}
                         editable={editable} />
            <IngredientListEdit ingredients={allIngredients}
                                onDelete={this.deleteIngredient}
                                onAdd={this.addIngredient}
                                clearAll={this.clearAllIngredients}
                                editalble={editable} />
          </div>
          <div className="block-body">
            <Comment />
            <Rating />
          </div>
        </div>
        <Footer />
      </>
    )
  }

  insert = () => {
    const {stepInsert, index, allSteps} = this.state
    const newStep = {id: index + 1, content: stepInsert}
    const preArray = allSteps
    this.setState({allSteps: [...allSteps, newStep], index: index + 1 })
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
        return this.renderFailureView()
      case apiStatusConstants.inProgress:
        return this.renderLoadingView()
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
