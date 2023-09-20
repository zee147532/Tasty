import {Component} from 'react'
import Loader from 'react-loader-spinner'
import Cookies from 'js-cookie'

import {RiArrowDropLeftLine, RiArrowDropRightLine} from 'react-icons/ri'

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
    keyword: '',
  }

  componentDidMount() {
    this.getRestaurants()
  }

  changeKeyword = keyword => {
    this.setState({keyword})
  }

  getRestaurants = async () => {
    this.setState({
      apiStatus: apiStatusConstants.inProgress,
    })
    const paging = this.props.paging
    const jwtToken = Cookies.get('jwt_token')
    const {activeOptionId, currentPage, keyword} = this.state
    const url = this.props.url
    const type = this.props.type
    let apiUrl
    if (type === 'own') {
        const username = this.props.getUsername()
      apiUrl = `http://localhost:8080/api/customer/${username}/posts`
    } else if (type === 'favorite') {
      apiUrl = 'http://localhost:8080/api/customer/favorite-posts'
    } else {
      apiUrl = `${url}?page=${currentPage}&paging=${paging}&pageSize=10&sortType=${activeOptionId}&keyword=${keyword}`
    }
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
        keyword: '',
      })
    } else {
      this.setState({
        apiStatus: apiStatusConstants.failure,
        keyword: ','
      })
    }
  }

  changeSortBy = activeOptionId => {
    this.setState({activeOptionId}, this.getRestaurants)
  }

  changeImage = async (file) => {
    this.setState({
      apiStatus: apiStatusConstants.inProgress,
    })
    var data = new FormData()
    data.append('file', file)
    const apiUrl = 'http://localhost:8080/api/customer/posts/search'
    const options = {
      // headers: myHeader,
      body: data,
      method: 'POST',
    }
    await fetch(apiUrl, options)
        .then(response => {
          const fetchedData = response.json()
          fetchedData.then(restaurants => {
            const updatedData = restaurants.map(restaurant => ({
                name: restaurant.name,
                cuisine: restaurant.cuisine,
                id: restaurant.id,
                imageUrl: restaurant.imageUrl,
                rating: restaurant.rating,
                totalReviews: restaurant.totalReviews,
              }
            ))
            this.setState({
              restaurantList: updatedData,
              apiStatus: apiStatusConstants.success,
              totalPage: 1,
            })
          })
        })
        .catch(() => {
          this.setState({
            apiStatus: apiStatusConstants.failure,
          })
        })
  }

  renderRestaurantListView = () => {
    const {restaurantList, activeOptionId, keyword} = this.state

    return (
      <>
        <RestaurantHeader
          activeOptionId={activeOptionId}
          sortByOptions={sortByOptions}
          changeSortBy={this.changeSortBy}
          search={this.getRestaurants}
          onChangeKeyword={this.changeKeyword}
          changeImage={this.changeImage}

        />
        <hr className="hr-line"/>
          {restaurantList.length === 0 ? (
              <div className="restaurant-error-view-container">
                  <p className="restaurant-failure-description">
                      Không thể tìm thấy bất kỳ bài viết nào!
                  </p>
              </div>
          ) : (
              <ul className="restaurant-list">
                  {restaurantList.map(restaurant => (
                      <RestaurantCard restaurant={restaurant} key={restaurant.id}/>
                  ))}
              </ul>
          )}
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
    const {currentPage, totalPage} = this.state
    const paging = this.props.paging
    const hasNext = currentPage < totalPage
    const hasPrev = currentPage > 1
    return (
      <div>
        {/*<ReactSlider />*/}
        <div className="all-restaurant-responsive-container">
          {this.renderRestaurants()}
          <div className={`restaurant-navigation ${paging ? '' : 'disable'}`}>
            <button
              type="button"
              className={`arrow-button ${hasPrev ? '' : 'un-clickable'}`}
              onClick={this.leftArrowClicked}
            >
              <RiArrowDropLeftLine size={25} color={`${hasPrev ? 'red' : 'grey'}`} className="arrow" />
            </button>
            <span className="current-page">{currentPage}</span>
            <button
              type="button"
              className={`arrow-button ${hasNext ? '' : 'un-clickable'}`}
              onClick={this.rightArrowClicked}
            >
              <RiArrowDropRightLine size={25} color={`${hasNext ? 'red' : 'grey'}`} className="arrow" />
            </button>
          </div>
        </div>
      </div>
    )
  }
}

export default AllRestaurantsList
