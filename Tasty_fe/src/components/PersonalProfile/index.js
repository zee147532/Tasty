import {Component} from "react";
import './index.css'
import AllRestaurantsList from "../AllRestaurantsList";
import Header from "../Header"
import Footer from "../Footer"
import Loader from "react-loader-spinner";
import Cookies from "js-cookie";

const apiStatusConstants = {
    initial: 'INITIAL',
    success: 'SUCCESS',
    failure: 'FAILURE',
    inProgress: 'IN_PROGRESS',
}

class PersonalProfile extends Component {
    state = {
        apiStatus: apiStatusConstants.initial,
        customerProfile: {},
    }
    componentDidMount() {
        this.loadCustomerDetail()
    }

    loadCustomerDetail = async () => {
        const {match} = this.props
        const {params} = match
        const {username} = params
        this.setState({
            apiStatus: apiStatusConstants.success,
        })

        const jwtToken = Cookies.get('jwt_token')
        const apiUrl = `http://localhost:8080/api/customer/profile/${username}`
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
                customerProfile: fetchedData,
                apiStatus: apiStatusConstants.success
            })
        } else {
            this.setState({apiStatus: apiStatusConstants.failure})
        }
    }

    renderLoadingView = () => (
        <div className="restaurant-loader-container">
            <Loader type="Oval" color="#F7931E" height="50" width="50"/>
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
                we are sorry, the page you requested could not be found Please go back
                to the homepage
            </p>
            <button className="error-button" type="button">
                Home Page
            </button>
        </div>
    )

    renderProfileContainer = () => {
        const {apiStatus} = this.state

        switch (apiStatus) {
            case apiStatusConstants.success:
                return this.renderProfileView()
            case apiStatusConstants.failure:
                return this.renderFailureView()
            case apiStatusConstants.inProgress:
                return this.renderLoadingView()
            default:
                return null
        }
    }

    renderProfileView = () => {
        const {customerProfile}  = this.state
        return (
            <>
                <header className="profile-header">
                    <div className="container">
                        <div className="profile">
                            <div className="profile-image">
                                <img src={`${(!customerProfile.imageUrl || customerProfile.imageUrl.length < 1) ? 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSheI9UkWllIpSNbs2UdE18KLLswgDON9qzXg&usqp=CAU' : customerProfile.imageUrl}`}  alt={''}/>
                            </div>
                            <div className="profile-user-settings">
                                <h1 className="profile-user-name">{customerProfile.username}
                                    {customerProfile.gender === 'NAM' && (<span className="material-symbols-rounded gender-icon">male</span>)}
                                    {customerProfile.gender === 'NU' && (<span className="material-symbols-rounded gender-icon">female</span>)}
                                    {customerProfile.gender === 'AN' && (<span className="material-symbols-rounded gender-icon">circle</span>)}
                                </h1>
                                <div className="dropdown">
                                    <button className="btn btn-secondary dropdown-toggle profile-settings-btn" type="button"
                                            id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true"
                                            aria-expanded="false">
                                        <i className="fas fa-cog" aria-hidden="true"></i>
                                    </button>
                                    <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                        <li><a className="dropdown-item" href="#" data-toggle="modal" data-target="#edit-profile">Chỉnh sửa</a></li>
                                        <li><a className="dropdown-item" href="#" data-toggle="modal" data-target="#change-password">Đổi mật khẩu</a></li>
                                    </ul>
                                </div>
                            </div>
                            <div className="profile-stats">
                                <ul>
                                    <li><span className="profile-stat-count">{customerProfile.totalPosts}</span> bài viết</li>
                                </ul>
                            </div>
                            <div className="profile-bio">
                                <p><span className="profile-real-name">{customerProfile.fullName}</span> <span className="material-symbols-rounded double-arrow">keyboard_double_arrow_right</span> {customerProfile.description}</p>
                            </div>
                        </div>
                        {/*End of profile section*/}
                    </div>
                    {/*End of container*/}
                </header>
                <main className="profile-body">
                    <div id="exTab3" className="container">
                        <ul  className="nav nav-pills">
                            <li className="active">
                                <a  href="#1b" data-toggle="tab">Bài viết</a>
                            </li>
                            <li>
                                <a href="#2b" data-toggle="tab">Yêu thích</a>
                            </li>
                        </ul>

                        <div className="tab-content clearfix">
                            <div className="tab-pane active" id="1b">
                                <AllRestaurantsList paging={false} url={"http://localhost:8080/api/customer/posts"}/>
                            </div>
                            <div className="tab-pane" id="2b">
                                <AllRestaurantsList paging={false} url={"http://localhost:8080/api/customer/posts"}/>
                            </div>
                        </div>
                    </div>
                </main>

                {/*The edit profile modal*/}
                <div className="modal fade" id="edit-profile" tabIndex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
                    <div className="modal-dialog" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                <h4 className="modal-title" id="modalLabel">Modal Title</h4>
                            </div>
                            <div className="edit-profile">
                                <div className="form-group">
                                    <label htmlFor="username">Tên đăng nhập</label>
                                    <input type="text" className="form-control" id="username" disabled value={customerProfile.username} name="username" />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="full_name">Họ tên <span className="require-label">*</span></label>
                                    <input type="text" className="form-control" id="full_name" name="full_name" />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="email">Email</label>
                                    <input type="text" className="form-control" id="email" name="email" disabled />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="phone_number">Số điện thoại</label>
                                    <input type="number" className="form-control" id="phone_number" name="phone_number" />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="gender">Giới tính <span className="require-label">*</span></label>
                                    <div className="gender-container">
                                        <div className="gender-radio">
                                            <input type="radio" name="gender" value="NU" onChange={this.onChangeGender}  /><label>Nữ</label>
                                        </div>
                                        <div className="gender-radio">
                                            <input type="radio" name="gender" value="NAM" onChange={this.onChangeGender} /><label>Nam</label>
                                        </div>
                                        <div className="gender-radio">
                                            <input type="radio" name="gender" value="AN" onChange={this.onChangeGender} /><label>Bí mật</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-secondary" data-dismiss="modal">Hủy</button>
                                <button type="button" className="btn btn-success" data-dismiss="modal">Lưu</button>
                            </div>
                        </div>
                    </div>
                </div>

                {/*The change password modal*/}
                <div className="modal fade" id="change-password" tabIndex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
                    <div className="modal-dialog" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                <h4 className="modal-title" id="modalLabel">Modal Title</h4>
                            </div>
                            <div className="modal-body">
                                Modal content...
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-secondary" data-dismiss="modal">Hủy</button>
                                <button type="button" className="btn btn-success" data-dismiss="modal">Lưu</button>
                            </div>
                        </div>
                    </div>
                </div>
            </>
        )
    }


    render() {
        return (
            <>
                <Header />
                {this.renderProfileContainer()}
                <Footer />
            </>
        )
    }
}

export default PersonalProfile