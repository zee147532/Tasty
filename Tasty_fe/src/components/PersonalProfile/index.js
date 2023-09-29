import {Component} from "react";
import './index.css'
import AllPostsList from "../AllPostsList";
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
        professionList: [],
        customerDetail: {},
        detailApiStatus: apiStatusConstants.inProgress,
        username: '',
        jwtToken: '',
        srcImage: '',
        loggedUser: '',
        oldPassword: '',
        newPassword: '',
        confirmPassword: '',
    }
    componentDidMount() {
        this.loadCustomerProfile()
    }

    loadCustomerProfile = async () => {
        const {match} = this.props
        const {params} = match
        const {username} = params
        const jwtToken = Cookies.get('jwt_token')
        const loggedUser = Cookies.get('username')
        this.setState({
            apiStatus: apiStatusConstants.success,
            username: username,
            loggedUser: loggedUser,
        })

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
                apiStatus: apiStatusConstants.success,
                jwtToken: jwtToken,
            })
        } else {
            this.setState({apiStatus: apiStatusConstants.failure})
        }
    }

    loadCustomerDetail = async () => {
        const {username, jwtToken} = this.state
        const apiUrl = `http://localhost:8080/api/customer/profile/${username}/detail`
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
                customerDetail: fetchedData,
            })
            await this.loadProfessions()
        } else {
            this.setState({detailApiStatus: apiStatusConstants.failure})
        }
    }

    loadProfessions = async () => {
        const url = 'http://localhost:8080/api/customer/professions'
        const options = {
            method: 'GET',
        }
        const response = await fetch(url, options)
        if (response.ok) {
            const data = await response.json()
            const professions = data.map(profession => ({
                id: profession.id,
                name: profession.name,
            }))
            professions.push({
                id: 0,
                name: 'Khác',
            })
            this.setState({professionList: professions,
                detailApiStatus: apiStatusConstants.success,
            })

        } else {
            this.setState({detailApiStatus: apiStatusConstants.failure})
        }
    }

    importImage = (e) => {
        const {customerDetail} = this.state
        const file = e.target.files[0]
        if (file) {
            customerDetail.imageFile = file
            const reader = new FileReader();
            reader.onload = this.loadImage;
            this.setState({
                customerDetail,
                srcImage: URL.createObjectURL(file)
            })
        } else {
            customerDetail.imageFile = undefined
            this.setState({customerDetail})
        }
    }

    loadImage = (e) =>  {
        this.setState({srcImage: e.target.result})
        console.log(e.target.result)
    }

    changeDescription = (e) => {
        const {customerDetail} = this.state
        customerDetail.description = e.target.value
        this.setState({customerDetail})
    }

    changeProfession = (e) => {
        const {customerDetail} = this.state
        customerDetail.professionId = e.target.value
        this.setState({customerDetail})
    }

    onChangeGender = (e) => {
        const {customerDetail} = this.state
        customerDetail.gender = e.target.value
        this.setState({customerDetail})
    }

    changePhoneNumber = (e) => {
        const {customerDetail} = this.state
        customerDetail.phoneNumber = e.target.value
        this.setState({customerDetail})
    }

    changeFullName = (e) => {
        const {customerDetail} = this.state
        customerDetail.fullName = e.target.value
        this.setState({customerDetail})
    }

    update = async (e) => {
        e.preventDefault();
        const {customerDetail, jwtToken} = this.state
        const apiUrl = 'http://localhost:8080/api/customer'
        const data = {
            'id': customerDetail.id,
            'username': customerDetail.username,
            'fullName': customerDetail.fullName,
            'phoneNumber': customerDetail.phoneNumber,
            'email': customerDetail.email,
            'gender': customerDetail.gender,
            'professionId': customerDetail.professionId,
            'description': customerDetail.description,
            'imageUrl': ''
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
        console.log(apiUrl)
        const response = await fetch(apiUrl, options)
        if (response.ok) {
            if(customerDetail.imageFile) {
                this.uploadAvatar()
            } else {
                this.loadCustomerProfile()
                alert("Cập nhật thông tin thành công.")
            }
        } else {
            const fetchedData = await response.json()
            alert(fetchedData.errorMsg)
        }
    }

    uploadAvatar = async () => {
        const jwtToken = Cookies.get('jwt_token')
        const {customerDetail} = this.state
        const url = `http://localhost:8080/api/customer/${customerDetail.username}/avatar`
        const data = new FormData();
        data.append('file', customerDetail.imageFile)

        const options = {
            headers: {
                Authorization: `Bearer ${jwtToken}`,
            },
            body: data,
            method: 'POST',
        }

        const response = await fetch(url, options)
        if (response.ok) {
            this.loadCustomerProfile()
            alert("Cập nhật thông tin thành công.")
        } else {
            const fetchedData = await response.json()
            alert(fetchedData.errorMsg)
        }

    }

    renderDetailView = () => {
        const {customerDetail, professionList, srcImage} = this.state

        return (
            <div className="edit-profile">
                <div className="form-group">
                    <label htmlFor="username">Tên đăng nhập</label>
                    <input type="text" className="form-control" id="username" disabled value={customerDetail.username} name="username" />
                </div>
                <div className="form-group">
                    <label htmlFor="full_name">Họ tên <span className="require-label">*</span></label>
                    <input
                        value={customerDetail.fullName} type="text" className="form-control"
                        id="full_name" name="fullName" onChange={this.changeFullName} />
                </div>
                <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <input value={customerDetail.email} type="text" className="form-control" id="email" name="email" disabled />
                </div>
                <div className="form-group">
                    <label htmlFor="phone_number">Số điện thoại</label>
                    <input
                        value={customerDetail.phoneNumber} type="number" className="form-control"
                        id="phone_number" name="phoneNumber" onChange={this.changePhoneNumber} />
                </div>
                <div className="form-group">
                    <label htmlFor="gender">Giới tính <span className="require-label">*</span></label>
                    <div className="gender-container">
                        <div className="gender-radio">
                            <input checked={customerDetail.gender === 'NU'} type="radio" name="gender" value="NU" onChange={this.onChangeGender} /><label>Nữ</label>
                        </div>
                        <div className="gender-radio">
                            <input checked={customerDetail.gender === 'NAM'} type="radio" name="gender" value="NAM" onChange={this.onChangeGender} /><label>Nam</label>
                        </div>
                        <div className="gender-radio">
                            <input checked={customerDetail.gender === 'AN'} type="radio" name="gender" value="AN" onChange={this.onChangeGender} /><label>Bí mật</label>
                        </div>
                    </div>
                </div>
                <div className="form-group">
                    <label htmlFor="profession">Nghề nghiệp</label>
                    <select className="form-control" name="professionId" value={customerDetail.professionId} onChange={this.changeProfession} >
                        <option value="" disabled selected hidden>Select</option>
                        {professionList.map(profession => (
                            <option value={profession.id}>{profession.name}</option>
                        ))}
                    </select>
                </div>
                <div className="form-group">
                    <label className="form-label" htmlFor="customFile">Ảnh đại diện</label>
                    <label className="picture" htmlFor="picture-input" tabIndex="0">
                        {customerDetail.imageFile ? (
                            <span className="picture-image">
                                <img src={srcImage} id="imported-image" className="picture-img" alt="preview image"/>
                            </span>
                        ) : (
                            <span className="picture-image" dangerouslySetInnerHTML={{__html: '<p>Chọn ảnh</p>'}}></span>
                        )}
                    </label>
                    <input type="file" accept="image/*" name="imageFile" id="picture-input" onChange={this.importImage}/>
                </div>
                <div className="form-group">
                    <label htmlFor="description">Mô tả bản thân</label>
                    <textarea
                        value={customerDetail.description} className="form-control" rows="4"
                        name="description" id="description" onChange={this.changeDescription} />
                </div>
            </div>
        )
    }

    renderDetailContainer = () => {
        const {detailApiStatus} = this.state

        switch (detailApiStatus) {
            case apiStatusConstants.success:
                return this.renderDetailView()
            case apiStatusConstants.failure:
                return this.renderFailureView()
            case apiStatusConstants.inProgress:
                return this.renderLoadingView()
            default:
                return null
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

    getUsername = () => {
        const {username} = this.state
        return username
    }

    changePassword = async () => {
        const {oldPassword, newPassword, confirmPassword, username} = this.state
        if (oldPassword === '' || newPassword === '' || confirmPassword === '') {
            alert("Vui lòng điền đầy đủ thông tin")
        } else if (oldPassword === newPassword) {
            alert("Bạn không thể sử dụng lại mật hiện tại.")
        } else if (newPassword !== confirmPassword) {
            alert("Mật khẩu xác nhận không trùng khớp.")
        } else {
            const jwtToken = Cookies.get('jwt_token')
            const apiUrl = 'http://localhost:8080/api/customer/password'
            const data = {
                username: username,
                oldPassword: oldPassword,
                newPassword: newPassword
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

            const response = await fetch(apiUrl, options)
            if (response.ok) {
                alert("Thay đổi mật khẩu thành công.")
            } else {
                const fetchedData = await response.json()
                alert(fetchedData.errorMsg)
            }
        }
        this.setState({
            oldPassword: '',
            newPassword: '',
            confirmPassword: ''
        })
    }

    renderProfileView = () => {
        const {customerProfile, username, loggedUser, oldPassword, newPassword, confirmPassword}  = this.state
        return (
            <>
                <header className="profile-header">
                    <div className="container">
                        <div className="profile">
                            <div className="profile-image">
                                <img className={"avatar"} src={`${(!customerProfile.imageUrl || customerProfile.imageUrl.length < 1) ? 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSheI9UkWllIpSNbs2UdE18KLLswgDON9qzXg&usqp=CAU' : customerProfile.imageUrl}`}  alt={''}/>
                            </div>
                            <div className="profile-user-settings">
                                <h1 className="profile-user-name">{customerProfile.username}
                                    {customerProfile.gender === 'NAM' && (<span className="material-symbols-rounded gender-icon">male</span>)}
                                    {customerProfile.gender === 'NU' && (<span className="material-symbols-rounded gender-icon">female</span>)}
                                    {customerProfile.gender === 'AN' && (<span className="material-symbols-rounded gender-icon">circle</span>)}
                                </h1>
                                {username === loggedUser && (
                                    <div className="dropdown">
                                        <button className="btn btn-secondary dropdown-toggle profile-settings-btn"
                                                type="button"
                                                id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true"
                                                aria-expanded="false">
                                            <i className="fas fa-cog" aria-hidden="true"></i>
                                        </button>
                                        <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                            <li onClick={() => {
                                                this.setState({detailApiStatus: apiStatusConstants.inProgress})
                                                this.loadCustomerDetail()
                                            }}>
                                                <a className="dropdown-item" href="#" data-toggle="modal"
                                                   data-target="#edit-profile">Chỉnh sửa</a>
                                            </li>
                                            <li><a className="dropdown-item" href="#" data-toggle="modal"
                                                   data-target="#change-password">Đổi mật khẩu</a></li>
                                        </ul>
                                    </div>
                                )}
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
                            {username === loggedUser && (
                                <li>
                                    <a href="#2b" data-toggle="tab">Yêu thích</a>
                                </li>
                            )}
                        </ul>

                        <div className="tab-content clearfix">
                            <div className="tab-pane active" id="1b">
                                <AllPostsList paging={false} type={"own"} header={false} getUsername={this.getUsername}/>
                            </div>
                            {username === loggedUser && (
                                <div className="tab-pane" id="2b">
                                    <AllPostsList paging={false} type={"favorite"} header={false}/>
                                </div>
                            )}
                        </div>
                    </div>
                </main>

                {/*The edit profile modal*/}
                <div className="modal fade" id="edit-profile" tabIndex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
                    <div className="modal-dialog" role="document">
                        <form onSubmit={this.update} id="form" className="modal-content">
                            <div className="modal-header">
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                <h4 className="modal-title" id="modalLabel">Cập nhật thông tin</h4>
                            </div>
                            {this.renderDetailContainer()}
                            <div className="modal-footer">
                                <button type="button" className="btn btn-secondary" data-dismiss="modal">Hủy</button>
                                <button type="submit" form="form" className="btn btn-success" data-dismiss="modal"
                                    onClick={this.update}>Lưu</button>
                            </div>
                        </form>
                    </div>
                </div>

                {/*The change password modal*/}
                <div className="modal fade" id="change-password" tabIndex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
                    <div className="modal-dialog change-password-container" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                <h4 className="modal-title" id="modalLabel">Đổi mật khẩu</h4>
                            </div>
                            <div className="modal-body">
                                <div className="edit-profile">
                                    <div className="form-group">
                                        <label htmlFor="old-password" aria-required={true}>Mật khẩu cũ</label>
                                        <div className="inputBox">
                                            <input value={oldPassword} type="password" onChange={(e) => {
                                                this.setState({
                                                    oldPassword: e.target.value
                                                })}} placeholder="Old password" id="myPassword"/>
                                        </div>
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="new-password">Mật khẩu mới</label>
                                        <div className="inputBox">
                                            <input value={newPassword} type="password" onChange={(e) => {
                                                this.setState({
                                                    newPassword: e.target.value
                                                })}} placeholder="New password" id="myPassword"/>
                                        </div>
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="confirm-password">Xác nhận mật khẩu</label>
                                        <div className="inputBox">
                                            <input value={confirmPassword} type="password" onChange={(e) => {
                                                this.setState({
                                                    confirmPassword: e.target.value
                                                })}} placeholder="Confirm password" id="myPassword"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-success" onClick={this.changePassword} data-dismiss="modal">Lưu</button>
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