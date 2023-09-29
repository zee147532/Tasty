import Footer from '../Footer'

import './index.css'
import {Link} from "react-router-dom";
import {Component} from "react";
import Cookies from "js-cookie";

class Home extends Component {
    state = {
        username: '',
    }

    onClickAllPosts = () => {
        const {history} = this.props
        history.push('/posts')
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

    checkLogin = () => {
        const {username} = this.state
        if (username && username.length > 0) {
            const {history} = this.props
            history.push('/posts/new')
        } else {
            alert("Bạn cần đăng nhập để có thể tạo một bài viết mới.")
        }
    }

    render() {
        const {username} = this.state
        return (
            <>
                <section id="home">
                    <nav id="nav">
                        <div className="nav-logo">
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
                            <div className="hamburger">
                                <a href="#"><i className="fas fa-bars "></i></a>
                            </div>
                        </div>
                        <ul className="nav-links">
                            <li><a href={"/"}>TRANG CHỦ</a></li>
                            <li><a href={"/posts"}>BÀI VIẾT</a></li>
                            <li><a onClick={this.checkLogin}>TẠO BÀI VIẾT</a></li>
                            {(username && username.length > 0) ? (
                                <>
                                    <li>
                                        <p className="username"><a className="nav-link"
                                                                   href={`/profile/${username}`}>{username} (Customer)</a></p>
                                    </li>
                                    <li>
                                        <button
                                            type="button"
                                            className="logout-desktop-btn"
                                            onClick={this.onClickLogout}
                                        >
                                            <b>Logout </b>
                                        </button>
                                    </li>
                                </>
                            ) : (
                                <li>
                                    <button
                                        type="button"
                                        className="logout-desktop-btn"
                                        onClick={this.onClickLogin}
                                    >
                                        <b>Login</b>
                                    </button>
                                </li>
                            )}
                        </ul>
                    </nav>
                    <div className="content">
                        <div className="text-content">
                            <h1 className="white">Cùng nhau tạo nên sự đa dạng</h1>
                            <h1 className="white">cho <strong>mỗi bữa ăn!</strong></h1>
                            <h4 className="white">We bring variety to <strong>your kitchen!</strong></h4>
                            <div className="two-button">
                                <button className="w-btn btn" onClick={this.onClickAllPosts}>Tất cả công thức</button>
                                <button className="t-btn btn" onClick={this.checkLogin}>Tạo mới</button>
                            </div>
                        </div>
                    </div>
                </section>
                <Footer/>
            </>
        )
    }
}

export default Home
