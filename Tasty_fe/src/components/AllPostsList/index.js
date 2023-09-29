import {Component} from 'react'
import Loader from 'react-loader-spinner'
import Cookies from 'js-cookie'

import {RiArrowDropLeftLine, RiArrowDropRightLine} from 'react-icons/ri'

import PostsHeader from '../PostsHeader'
import PostsCard from '../PostsCard'

import './index.css'

const sortByOptions = [
  {
    id: 0,
    displayText: 'Mới nhất',
    value: 'DESC',
  },
  {
    id: 2,
    displayText: 'Cũ nhất',
    value: 'ASC',
  },
]

const apiStatusConstants = {
  initial: 'INITIAL',
  success: 'SUCCESS',
  failure: 'FAILURE',
  inProgress: 'IN_PROGRESS',
}

class AllPostsList extends Component {
  state = {
    postsList: [],
    activeOptionId: 'DESC',
    currentPage: 1,
    apiStatus: apiStatusConstants.initial,
    totalPage: 99,
    keyword: '',
    header: true,
  }

  componentDidMount() {
    const {header} = this.props
    if (header !== undefined) {
      this.setState({header: header})
    }
    this.getPosts()
  }

  changeKeyword = keyword => {
    this.setState({keyword})
  }

  getPosts = async () => {
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
      const updatedData = fetchedData.data.map(posts => ({
        name: posts.name,
        cuisine: posts.cuisine,
        id: posts.id,
        imageUrl: posts.imageUrl,
        rating: posts.rating,
        totalReviews: posts.totalReviews,
      }))
      this.setState({
        postsList: updatedData,
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
    this.setState({activeOptionId}, this.getPosts)
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
          fetchedData.then(posts => {
            const updatedData = posts.map(post => ({
                name: post.name,
                cuisine: post.cuisine,
                id: post.id,
                imageUrl: post.imageUrl,
                rating: post.rating,
                totalReviews: post.totalReviews,
              }
            ))
            this.setState({
              postsList: updatedData,
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

  renderPostsListView = () => {
    const {postsList, activeOptionId, keyword, header} = this.state

    return (
      <>
        {header && (
            <PostsHeader
                activeOptionId={activeOptionId}
                sortByOptions={sortByOptions}
                changeSortBy={this.changeSortBy}
                search={this.getPosts}
                onChangeKeyword={this.changeKeyword}
                changeImage={this.changeImage}
            />
        )}
        <hr className="hr-line"/>
        {postsList.length === 0 ? (
            <div className="restaurant-error-view-container">
              <p className="restaurant-failure-description">
                Không thể tìm thấy bất kỳ bài viết nào!
              </p>
            </div>
        ) : (
            <ul className="restaurant-list">
              {postsList.map(posts => (
                  <PostsCard posts={posts} key={posts.id}/>
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

  renderPosts = () => {
    const {apiStatus} = this.state

    switch (apiStatus) {
      case apiStatusConstants.success:
        return this.renderPostsListView()
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
        this.getPosts,
      )
    }
  }

  rightArrowClicked = () => {
    const {currentPage, totalPage} = this.state
    if (currentPage < totalPage) {
      this.setState(
        prev => ({currentPage: prev.currentPage + 1}),
        this.getPosts,
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
          {this.renderPosts()}
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

export default AllPostsList
