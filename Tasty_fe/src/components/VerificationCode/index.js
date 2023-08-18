import Cookies from 'js-cookie'
import {Component} from "react";
import './index.css'

class VerificationCode extends Component {
    state = {
        showSubmitError: false,
        errorMsg: '',
        code: '',
    }

    changeCode = event => {
        this.setState({code: event.target.value, showSubmitError: false, errorMsg: ''})
    }

    msgErr = msg => {
        this.setState({showSubmitError: true, errorMsg: msg})
    }

    onSubmitSuccess = () => {
        const {history} = this.props
        history.push('/registry/success')
    }
    verify = async event => {
        event.preventDefault()
        const email = Cookies.get('email')
        const {code} = this.state
        if (code.length !== 6) {
            this.msgErr("Mã xác thực không hợp lệ. Vui lòng thử lại.")
            return
        }
        const verifyRequest = {
            email: email,
            verifyCode: code
        }
        const url = 'http://localhost:8080/api/registry/verify?email=' + email
        const options = {
            method: 'POST',
            body: JSON.stringify(verifyRequest),
            headers: {
                Accept: 'application/json',
                'Content-type': 'application/json',
            },
        }
        const response = await fetch(url, options)
        const data = await response.json()
        if (response.ok === true) {
            this.onSubmitSuccess()
        } else {
            this.msgErr(data.msg)
        }
    }

    resendCode = async () => {
        const email = Cookies.get('email')
        const url = 'http://localhost:8080/api/registry/resend-code?email=' + email
        const options = {
            method: 'POST',
        }
        const response = await fetch(url, options)
        const data = await response.json()
        if (response.ok === true) {
            this.hideMsg()
        } else {
            this.msgErr(data)
        }
    }

    hideMsg = () => {
        this.setState({showSubmitError: false, errorMsg: ''})
    }

    render() {
        const email = Cookies.get('email')
        console.log(email)
        const {showSubmitError, errorMsg} = this.state
        return (
            <form action="#" className="verifyForm" onSubmit={this.verify}>
                <p className="title">Mã xác thực</p>
                <p className="text">Vui lòng nhập mã xác thực được gửi đến địa chỉ email <span className="email">{email}</span> để hoàn thành quá trình đăng ký tài khoản.</p>
                <p className="text">Mã xác thực sẽ có hiệu lực trong 5 phút.</p>
                <div className="d-flex mb-3">
                    <input className="verification-code" placeholder="______" maxLength={6} onChange={this.changeCode} />
                </div>
                {showSubmitError && <p className="error-message">{errorMsg}</p>}
                <button type="submit" className="verification-button">Xác thực tài khoản</button>
                <div className="single-line"></div>
                <p className="resend-text">Bạn không nhận được email? <a className="resend-button" onClick={this.resendCode}>Gửi lại mã.</a></p>
            </form>
        )
    }
}

export default VerificationCode