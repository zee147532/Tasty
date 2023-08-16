import {Component} from 'react'
import Loader from 'react-loader-spinner'
import Cookies from 'js-cookie'

import {RiArrowDropLeftLine, RiArrowDropRightLine} from 'react-icons/ri'

import ReactSlider from '../ReactSlider'
import RestaurantHeader from '../RestaurantHeader'
import RestaurantCard from '../RestaurantCard'

import './index.css'

const sortByOptions = [
  {
    id: 0,
    displayText: 'Cao nhất',
    value: 'DESC',
  },
  {
    id: 2,
    displayText: 'Thấp nhất',
    value: 'ASC',
  },
]

const apiStatusConstants = {
  initial: 'INITIAL',
  success: 'SUCCESS',
  failure: 'FAILURE',
  inProgress: 'IN_PROGRESS',
}

class AllRestaurantsList extends Component {
  state = {
    restaurantList: [],
    activeOptionId: 'DESC',
    currentPage: 1,
    apiStatus: apiStatusConstants.initial,
    totalPage: 99,
    keyword: "",
  }

  componentDidMount() {
    this.getRestaurants()
  }

  getRestaurants = async () => {
    this.setState({
      apiStatus: apiStatusConstants.inProgress,
    })
    const jwtToken = Cookies.get('jwt_token')
    const {activeOptionId, currentPage, keyword} = this.state
    const apiUrl = `http://localhost:8080/api/customer/posts?page=${currentPage}&sortType=${activeOptionId}`
    const options = {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
      method: 'GET',
    }
    const response = await fetch(apiUrl, options)
    if (response.ok) {
      const fetchedData = await response.json()
      const updatedData = fetchedData.data.map(restaurant => ({
        name: restaurant.name,
        cuisine: restaurant.cuisine,
        id: restaurant.id,
        imageUrl: restaurant.imageUrl,
        rating: restaurant.rating,
        totalReviews: restaurant.totalReviews,
      }))
      this.setState({
        restaurantList: updatedData,
        apiStatus: apiStatusConstants.success,
        totalPage: fetchedData.totalPage,
      })
    } else {
      this.setState({
        apiStatus: apiStatusConstants.failure,
      })
    }
  }

  changeSortBy = activeOptionId => {
    this.setState({activeOptionId}, this.getRestaurants)
  }

  renderRestaurantListView = () => {
    const {restaurantList, activeOptionId} = this.state

    return (
      <>
        <RestaurantHeader
          activeOptionId={activeOptionId}
          sortByOptions={sortByOptions}
          changeSortBy={this.changeSortBy}
        />
        <hr className="hr-line" />
        <ul className="restaurant-list">
          {restaurantList.map(restaurant => (
            <RestaurantCard restaurant={restaurant} key={restaurant.id} />
          ))}
        </ul>
      </>
    )
  }

  renderFailureView = () => (
    <div className="restaurant-error-view-container">
      <img
        src="https://res.cloudinary.com/djjbttpq0/image/upload/v1641968177/Tasty%20Kitchens/erroring_1x_x7gtp8.png"
        alt="restaurants failure"
        className="restaurant-failure-img"
      />
      <h1 className="restaurant-failure-heading-text">Page Not Found</h1>
      <p className="restaurant-failure-description">
        we are sorry, the page you requested could not be found Please go back
        to the homepage
      </p>
      <button className="error-button" type="button">
        Home Page
      </button>
    </div>
  )

  renderLoadingView = () => (
    <div className="restaurant-loader-container">
      <Loader type="Oval" color="#F7931E" height="50" width="50" />
    </div>
  )

  renderRestaurants = () => {
    const {apiStatus} = this.state

    switch (apiStatus) {
      case apiStatusConstants.success:
        return this.renderRestaurantListView()
      case apiStatusConstants.failure:
        return this.renderFailureView()
      case apiStatusConstants.inProgress:
        return this.renderLoadingView()
      default:
        return null
    }
  }

  leftArrowClicked = () => {
    const {currentPage} = this.state
    if (currentPage > 1) {
      this.setState(
        prev => ({currentPage: prev.currentPage - 1}),
        this.getRestaurants,
      )
    }
  }

  rightArrowClicked = () => {
    const {currentPage, totalPage} = this.state
    if (currentPage < totalPage) {
      this.setState(
        prev => ({currentPage: prev.currentPage + 1}),
        this.getRestaurants,
      )
    }
  }

  render() {
    const {currentPage} = this.state
    return (
      <div>
        {/*<ReactSlider />*/}
        <div className="all-restaurant-responsive-container">
          {this.renderRestaurants()}
          <div className="restaurant-navigation">
            <button
              type="button"
              className="arrow-button"
              onClick={this.leftArrowClicked}
            >
              <RiArrowDropLeftLine size={20} color="red" className="arrow" />
            </button>
            <span className="current-page">{currentPage}</span>
            <button
              type="button"
              className="arrow-button"
              onClick={this.rightArrowClicked}
            >
              <RiArrowDropRightLine size={20} color="red" className="arrow" />
            </button>
          </div>
        </div>
      </div>
    )
  }
}

export default AllRestaurantsList
