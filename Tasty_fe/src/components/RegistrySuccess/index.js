import {Component} from "react";
import '../VerificationCode/index.css'
import Cookies from "js-cookie";

class RegistrySuccess extends Component {
    redirectLogin = () => {
        const {history} = this.props
        Cookies.remove('jwt_token')
        Cookies.remove('username')
        Cookies.remove('email')
        history.push('/login')
    }
    render() {
        return (
            <form action="#" className="verifyForm" >
                <p className="title">Bạn đã đăng ký tài khoản thành công</p>
                <button type="submit" className="verification-button" onClick={this.redirectLogin}>Quay về trang đăng nhập</button>
            </form>
        )
    }
}

export default RegistrySuccess