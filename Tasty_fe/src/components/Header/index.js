import {Component} from 'react'
import {Link, withRouter} from 'react-router-dom'

import Cookies from 'js-cookie'

import './index.css'

class Header extends Component {
    state = {
        isMobileMenuClicked: false,
        username: '',
    }

    onClickLogout = () => {
        var warning = "Bạn có chắc muốn đăng xuất."
        if (confirm(warning)) {
            Cookies.remove('jwt_token')
            Cookies.remove('username')
            this.loadCustomer()
        }
    }

    onClickLogin = () => {
        const {history} = this.props
        Cookies.remove('jwt_token')
        Cookies.remove('username')
        history.push('/login')
    }

    loadCustomer = () => {
        const username = Cookies.get('username')
        this.setState({username})
    }

    componentDidMount() {
        this.loadCustomer()
    }

    onClickMenuBar = () => {
        this.setState(prev => ({isMobileMenuClicked: !prev.isMobileMenuClicked}))
    }

    onCloseClicked = () => {
        this.setState(prev => ({isMobileMenuClicked: !prev.isMobileMenuClicked}))
    }

    checkLogin = () => {
        const {username} = this.state
        if (username && username.length > 0) {
            const {history} = this.props
            history.push('/posts/new')
            window.location.reload(true)
        } else {
            alert("Bạn cần đăng nhập để có thể tạo một bài viết mới.")
        }
    }

    render() {
        const {isMobileMenuClicked, username} = this.state
        return (
            <>
                <nav className="nav-header">
                    <div className="nav-content">
                        <div className="nav-bar-mobile-logo-container">
                            <Link to="/" className="nav-link">
                                <div className="header-logo-container">
                                    <img
                                        className="website-logo"
                                        src="https://res.cloudinary.com/nsp/image/upload/v1635311275/tastyKitchens/websiteLogo_1x_fzy1tx.png"
                                        alt="website logo"
                                    />
                                    <p className="logo-name">Tasty Kitchens</p>
                                </div>
                            </Link>
                            <div className="mobile-action">
                            {(username && username.length > 0) && (
                                <p className="username"><a className="nav-link" href={`/profile/${username}`}>{username} (Customer)</a></p>
                            )}
                            <button type="button" className="nav-mobile-btn">
                                <img
                                    src="https://res.cloudinary.com/nsp/image/upload/v1635332660/tastyKitchens/menu_1x_fcu8zv.png"
                                    alt="nav menu"
                                    className="nav-bar-image"
                                    onClick={this.onClickMenuBar}
                                />
                            </button>
                            </div>
                        </div>

                        <div className="nav-bar-large-container">
                            <Link to="/" className="nav-link">
                                <div className="header-logo-container">
                                    <img
                                        className="website-logo"
                                        src="https://res.cloudinary.com/nsp/image/upload/v1635311275/tastyKitchens/websiteLogo_1x_fzy1tx.png"
                                        alt="website logo"
                                    />
                                    <p className="logo-name">Tasty Kitchens</p>
                                </div>
                            </Link>
                            <ul className="nav-menu">
                                <li className="nav-menu-item">
                                    <Link to="/" className="nav-link">
                                        Trang chủ
                                    </Link>
                                </li>

                                <li className="nav-menu-item">
                                    <Link to="/posts"  className="nav-link">
                                        Bài viết
                                    </Link>
                                </li>

                                <li className="nav-menu-item">
                                    <a onClick={this.checkLogin} className="nav-link">
                                        Tạo bài viết
                                    </a>
                                </li>
                            </ul>
                            {(username && username.length > 0) ? (
                                <>
                                    <p className="username"><a className="nav-link" href={`/profile/${username}`}>{username} (Customer)</a></p>
                                    <button
                                        type="button"
                                        className="logout-desktop-btn"
                                        onClick={this.onClickLogout}
                                    >
                                        <b>Logout </b>
                                    </button>
                                </>
                            ) : (
                                <button
                                    type="button"
                                    className="logout-desktop-btn"
                                    onClick={this.onClickLogin}
                                >
                                    <b>Login</b>
                                </button>
                            )}
                        </div>
                    </div>
                </nav>
                {isMobileMenuClicked && (
                    <div className="nav-mobile-only-menu">
                        <div className="nav-menu-mobile">
                            <div className="nav-menu-container">
                                <ul className="nav-menu-list-mobile">
                                    <li className="nav-menu-item-mobile">
                                        <Link to="/" className="nav-link">
                                            Trang chủ
                                        </Link>
                                    </li>

                                    <li className="nav-menu-item-mobile">
                                        <Link to="/cart" className="nav-link">
                                            Bài viết
                                        </Link>
                                    </li>

                                    <li className="nav-menu-item-mobile">
                                        <Link to="/posts/new" className="nav-link">
                                            Tạo bài viết
                                        </Link>
                                    </li>
                                </ul>
                                <button
                                    type="button"
                                    className="logout-desktop-btn-mobile"
                                    onClick={this.onClickLogout}
                                >
                                    Logout
                                </button>
                            </div>
                            <img
                                src="https://res.cloudinary.com/nsp/image/upload/v1635332590/tastyKitchens/Shape_vud3fu.png"
                                alt="nav close"
                                className="nav-bar-image"
                                onClick={this.onCloseClicked}
                            />
                        </div>
                    </div>
                )}
            </>
        )
    }
}

export default withRouter(Header)
