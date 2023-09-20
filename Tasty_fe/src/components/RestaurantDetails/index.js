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
    editable: false,
    isAuthor: false,
    postsId: '',
    srcImage: '',
    author: '',
    isFavorite: false,
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

  importImage = (e) => {
    const {restaurantData} = this.state
    const file = e.target.files[0]
    if (file) {
      restaurantData.imageFile = file
      const reader = new FileReader();
      reader.onload = this.loadImage;
      this.setState({
        restaurantData,
        srcImage: URL.createObjectURL(file)
      })
    } else {
      restaurantData.imageFile = undefined
      this.setState({restaurantData})
    }
  }

  loadImage = (e) =>  {
    this.setState({srcImage: e.target.result})
    console.log(e.target.result)
  }

  getRestaurantData = async () => {
    const {match} = this.props
    const {params} = match
    const {id} = params
    const username = Cookies.get('username')

    this.setState({postsId: id})

    if (id == 'new') {
      this.setState({
        apiStatus: apiStatusConstants.success,
        editable: true,
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
        isAuthor: username === fetchedData.author,
        author: fetchedData.author,
        isFavorite: fetchedData.favorite
      })
      console.log(fetchedData.favorite)
    } else {
      this.setState({
        apiStatus: apiStatusConstants.failure,
      })
    }
  }

  setTitle = (e) => {
    const {restaurantData} = this.state
    restaurantData.title = e.target.value
    console.log(restaurantData.title)
    this.setState({restaurantData})
  }

  setDescription = (e) => {
    const {restaurantData} = this.state
    restaurantData.description = e.target.value
    this.setState({restaurantData})
  }

  savePosts = async () => {
    const jwtToken = Cookies.get('jwt_token')
    const {restaurantData, allSteps, allIngredients, postsId} = this.state
    const url = 'http://localhost:8080/api/customer/posts'
    const data = {
      'id': restaurantData.id !== undefined ? restaurantData.id : null,
      'title': restaurantData.title,
      'description': restaurantData.description !== undefined ? restaurantData.description : '',
      'steps': allSteps,
      'ingredient': allIngredients
    }
    const options = {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
        Accept: 'application/json',
        'Content-type': 'application/json',
      },
      body: JSON.stringify(data),
      method: 'POST',
    }
    const response = await fetch(url, options)
    if (response.ok) {
      const id = await response.json()
      if (restaurantData.imageFile) {
        await this.saveImage(id)
      }
      this.setState({editable: false})
      const {history} = this.props
      history.push(`/posts/${id}`)
    } else {
      const data = await response.json()
      alert(data.errorMsg)
    }
  }

  saveImage = async (id) => {
    const jwtToken = Cookies.get('jwt_token')
    const {restaurantData} = this.state
    const url = `http://localhost:8080/api/customer/posts/${id}/image`
    const data = new FormData();
    data.append('file', restaurantData.imageFile)

    const options = {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
      body: data,
      method: 'POST',
    }

    await fetch(url, options)
    this.addOtherName()
    await this.getRestaurantData()
  }

  addOtherName = () => {
    const jwtToken = Cookies.get('jwt_token')
    const {restaurantData} = this.state
    const url = `http://localhost:8080/api/posts/${restaurantData.id}/other-name`
    const data = new FormData();
    const file = restaurantData.imageFile
    data.append('file', file)

    const options = {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
      body: data,
      method: 'POST',
    }

    fetch(url, options)
  }

  deletePosts = async () => {
    var warning = "Bạn có chắc muốn xóa bài viết."
    if (confirm(warning)) {
      const {restaurantData} = this.state
      const url = `http://localhost:8080/api/customer/posts/${restaurantData.id}`

      const options = {
        method: 'DELETE',
      }
      const response = await fetch(url, options)
      if (response.ok) {
        alert("Xóa bài viết thành công.")
        const {history} = this.props
        history.push("/posts")

      } else {
        const data = await response.json()
        alert(data.errorMsg)
      }

    }

  }

  removeFavorite = () => {
    const {restaurantData} = this.state
    const jwtToken = Cookies.get('jwt_token')
    const username = Cookies.get('username')
    if (username === undefined) {
      alert("Bạn cần đăng nhập để có thể xóa bài viết khỏi danh sách yêu thích.")
      return
    }
    const url = `http://localhost:8080/api/customer/favorite-posts/${restaurantData.id}`

    const options = {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
      method: 'DELETE',
    }
    fetch(url, options)
    this.setState({isFavorite: false})
  }

  addFavorite = () => {
    const {restaurantData} = this.state
    const jwtToken = Cookies.get('jwt_token')
    const username = Cookies.get('username')
    if (username === undefined) {
      alert("Bạn cần đăng nhập để có thể thêm bài viết khỏi danh sách yêu thích.")
      return
    }
    const url = `http://localhost:8080/api/customer/favorite-posts/${restaurantData.id}`

    const options = {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
      method: 'POST',
    }
    fetch(url, options)
    this.setState({isFavorite: true})
  }

  renderRestaurantDetailsView = () => {
    const {postsId, restaurantData, allSteps, allIngredients, editable, srcImage, isAuthor, author, isFavorite} = this.state

    return (
      <>
        <div className="specific-restaurant-details-container">
          <div className="restaurant-banner-container">
            <div className="banner-responsive-container">
              {postsId !== 'new' && !editable ? (
                  <>
                    <img
                        src={restaurantData.imageUrl}
                        alt="restaurant"
                        className="specific-restaurant-image"/>

                    <div className="banner-details-container">
                      <h1 className="specific-restaurant-name">{restaurantData.title}</h1>

                      <p className="author">{author}</p>
                      <div className="specific-restaurant-cuisine">
                        {restaurantData.tags?.map(tag => {
                          <p>{tag}</p>
                        })}
                      </div>
                      <p className="specific-restaurant-location">{restaurantData.description}</p>
                      <div className="rating-cost-container">
                        <hr className="line"/>
                        <div className="specific-restaurant-rating-container">
                          <div className="rating-container">
                            <AiFillStar className="restaurant-details-star"/>
                            <p className="specific-restaurant-rating">{restaurantData.rating}</p>
                          </div>
                          <p className="specific-restaurant-reviews">
                            {restaurantData.totalReviews} Ratings
                          </p>
                        </div>
                      </div>
                    </div>
                  </>
              ) : (
                  <>
                    <label className="picture" htmlFor="picture-input" tabIndex="0">
                      {restaurantData.imageFile ? (
                          <span className="picture-image">
                            <img src={srcImage} id="imported-image" className="picture-img" alt="preview image"/>
                          </span>
                      ) : (
                          <span className="picture-image" dangerouslySetInnerHTML={{__html: '<p>Chọn ảnh</p>'}}></span>
                      )}
                    </label>
                    <input type="file" accept="image/jpeg" name="imageFile" id="picture-input" onChange={this.importImage}/>
                    <div className="banner-details-container edit">
                      <input type={"text"}
                             className="specific-restaurant-name name-input"
                             value={restaurantData.title}
                             onChange={this.setTitle}
                             placeholder={"Tên món"}
                      />
                      <textarea rows={3}
                                className={"specific-restaurant-location description-input"}
                                value={restaurantData.description}
                                onChange={this.setDescription}
                                placeholder={"Mô tả..."}/>
                    </div>
                  </>
              )}
            </div>
            {((isAuthor && editable) || (postsId === 'new')) && (
                <div className="update-action">
                  <button type="button" className="btn btn-success" onClick={this.savePosts}>Lưu</button>
                  {postsId !== 'new' && (
                      <button type="button" className="btn btn-default"
                              onClick={() => this.setState({editable: false})}>Hủy</button>
                  )}
                </div>
            )}
            {!editable && (
                <div className="action-option dropdown">
                  <button className="btn btn-secondary dropdown-toggle option-button" type="button"
                          id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true"
                          aria-expanded="false">
                    <span className="material-symbols-rounded option-icon">
                      more_horiz
                    </span>
                  </button>
                  <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    {isAuthor && !editable && (
                        <>
                          <li onClick={() => this.setState({editable: true})}>
                            <a className="dropdown-item" href="#" data-toggle="modal">Chỉnh sửa</a>
                          </li>
                          <li>
                            <a className="dropdown-item" href="#" data-toggle="modal"
                               onClick={() => this.deletePosts()}>Xóa</a>
                          </li>
                        </>
                    )}
                    {isFavorite ? (
                        <li>
                          <a className="dropdown-item" onClick={this.removeFavorite}>Bỏ yêu thích</a>
                        </li>
                    ) : (
                        <li>
                          <a className="dropdown-item" onClick={this.addFavorite} >Thêm yêu thích</a>
                        </li>
                    )
                    }

                  </ul>
                </div>
            )}
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
          {(postsId !== 'new') && (
              <div className="block-body">
                <Comment postsId={postsId}/>
                <Rating postsId={postsId}/>
              </div>
          )}
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
