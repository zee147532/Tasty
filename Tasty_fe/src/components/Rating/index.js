import {Component} from "react";
import './index.css'
import Cookies from "js-cookie";
import Loader from "react-loader-spinner";

const apiStatusConstants = {
    initial: 'INITIAL',
    success: 'SUCCESS',
    failure: 'FAILURE',
    inProgress: 'IN_PROGRESS',
}

class Rating extends Component {
    state = {
        apiStatus: apiStatusConstants.initial,
        rate: 0,
        logged: false,
        posts: 3,
        comment: '',
    }

    componentDidMount() {
        const username = Cookies.get('username')
        if (username || true) {
            this.getRateByUsername()
            this.setState({logged: true})
        }
    }

    getRateByUsername = async () => {
        const {posts} = this.state
        this.setState({
            apiStatus: apiStatusConstants.inProgress,
        })
        const jwtToken = Cookies.get('jwt_token')
        const apiUrl = `http://localhost:8080/api/customer/posts/${posts}/rating`
        const options = {
            headers: {
                Authorization: `Bearer ${jwtToken}`,
            },
            method: 'GET',
        }
        const response = await fetch(apiUrl, options)
        if (response.ok) {
            const fetchedData = await response.json()
            this.setState({
                rate: fetchedData.rate,
                comment: fetchedData.comment,
                apiStatus: apiStatusConstants.success
            })
        } else {
            this.setState({apiStatus: apiStatusConstants.failure})
        }
    }

    renderRatingView = () => {
        const {rate} = this.state
        return (
            <>
                <p className="comment-title">Đánh giá:</p>
                <p className={`rating-title ${rate !== 0 ? 'disable' : ''}`}>Bạn chưa từng đánh giá món ăn này!</p>
                <div className="rating-div">
                    <div className="rating-group">
                        <input disabled defaultChecked={rate === 0} className="rating__input rating__input--none"
                               name="rating" id="rating3-none" value="0" type="radio"/>
                        <label aria-label="1 star" className="rating__label" htmlFor="rating3-1">
                            <i className="rating__icon rating__icon--star fa fa-star"></i>
                        </label>
                        <input defaultChecked={rate === 1} className="rating__input" name="rating" id="rating3-1"
                               value="1" type="radio"
                               onClick={() => this.setState({rate: 1})}/>
                        <label aria-label="2 stars" className="rating__label" htmlFor="rating3-2">
                            <i className="rating__icon rating__icon--star fa fa-star"></i>
                        </label>
                        <input defaultChecked={rate === 2} className="rating__input" name="rating" id="rating3-2"
                               value="2" type="radio"
                               onClick={() => this.setState({rate: 2})}/>
                        <label aria-label="3 stars" className="rating__label" htmlFor="rating3-3">
                            <i className="rating__icon rating__icon--star fa fa-star"></i>
                        </label>
                        <input defaultChecked={rate === 3} className="rating__input" name="rating" id="rating3-3"
                               value="3" type="radio"
                               onClick={() => this.setState({rate: 3})}/>
                        <label aria-label="4 stars" className="rating__label" htmlFor="rating3-4">
                            <i className="rating__icon rating__icon--star fa fa-star"></i>
                        </label>
                        <input defaultChecked={rate === 4} className="rating__input" name="rating" id="rating3-4"
                               value="4" type="radio"
                               onClick={() => this.setState({rate: 3})}/>
                        <label aria-label="5 stars" className="rating__label" htmlFor="rating3-5">
                            <i className="rating__icon rating__icon--star fa fa-star"></i>
                        </label>
                        <input defaultChecked={rate === 5} className="rating__input" name="rating" id="rating3-5"
                               value="5" type="radio"
                               onClick={() => this.setState({rate: 5})}/>
                    </div>
                    <div id="rating-action" className="rating-action">
                        <button type="button" className="save-rating btn btn-success"><p>Lưu</p>
                            <span className="material-symbols-rounded save-rating-icon">done</span>
                        </button>
                        <button type="button" className="cancel-rating btn btn-secondary"><p>Hủy</p>
                            <span className="material-symbols-rounded cancel-rating-icon">close</span>
                        </button>
                    </div>
                </div>
            </>
        )
    }

    renderFailureView = () => (
        <div className="restaurant-error-view-container">
            <img
                src="https://res.cloudinary.com/nsp/image/upload/v1635664104/tastyKitchens/error_1x_csgpog.png"
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
            <Loader type="Oval" color="#F7931E" height="50" width="50"/>
        </div>
    )

    renderRatingContainer = () => {
        const {apiStatus} = this.state

        switch (apiStatus) {
            case apiStatusConstants.success:
                return this.renderRatingView()
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
            <div className="rating-container-1">
                {this.renderRatingContainer()}
            </div>
        )
    }
}

export default Rating