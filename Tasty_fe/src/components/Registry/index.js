import {Component} from 'react'
import Cookies from 'js-cookie'
import {Redirect} from 'react-router-dom'
import image from './logo.png'

import '../Login/index.css'

class Registry extends Component {
  state = {
    username: '',
    password: '',
    confirmPass: '',
    showSubmitError: false,
    errorMsg: '',
    usernameErr: '',
    passwordErr: '',
  }

  onChangeUsername = event => {
    this.setState({username: event.target.value})
  }

  onChangePassword = event => {
    this.setState({password: event.target.value})
  }

  onChangeConfirmPassword = event => {
    this.setState({confirmPass: event.target.value})
  }

  onSubmitSuccess = username => {
    const {history} = this.props

    Cookies.set('username', username, {
      expires: 1800,
      path: '/',
    })
    history.push('/info-page')
  }

  onSubmitFailure = errorMsg => {
    this.setState({showSubmitError: true, errorMsg, usernameErr: '', passwordErr: ''})
  }
  errorUsername = usernameErr => {
    this.setState({showSubmitError: true, usernameErr, errorMsg: '', passwordErr: ''})
  }
  errorPassword = passwordErr => {
    this.setState({showSubmitError: true, passwordErr, usernameErr: '', errorMsg: ''})
  }

  submitForm = async event => {
    event.preventDefault()
    const {username, password, confirmPass} = this.state
    if (username === '') {
      this.errorUsername('Tên đăng nhập không được để trống.')
      return
    }
    if (username.length < 5) {
      this.errorUsername('Tên đăng nhập không được phép ít hơn 5 ký tự.')
      return
    }
    if (password === '') {
      this.errorPassword('Mật khẩu không được để trống.')
      return
    }
    if (password.length < 6) {
      this.errorPassword('Mật khẩu không được phép ít hơn 6 ký tự.')
      return
    }
    if (confirmPass === '') {
      this.onSubmitFailure('Vui lòng xác nhận mật khẩu.')
      return
    }
    if (password === confirmPass) {
      const userDetails = {username, password}
      const url = 'http://localhost:8080/api/registry'
      const options = {
        method: 'POST',
        body: JSON.stringify(userDetails),
        headers: {
          Accept: 'application/json',
          'Content-type': 'application/json',
        },
      }
      const response = await fetch(url, options)
      const data = await response.json()
      if (response.ok === true) {
        this.onSubmitSuccess(data.username)
      } else {
        this.onSubmitFailure(data.errorMsg)
      }
    } else {
      this.onSubmitFailure('Mật khẩu không trùng khớp.')
    }

  }

  renderPasswordField = () => {
    const {password} = this.state
    return (
      <>
        <label className="input-label" htmlFor="password">
          Mật khẩu
        </label>
        <input
          type="password"
          id="password"
          className="password-input-field"
          value={password}
          onChange={this.onChangePassword}
          placeholder="Password"
        />
      </>
    )
  }

  renderConfirmPasswordField = () => {
    const {confirmPass} = this.state
    return (
      <>
        <label className="input-label" htmlFor="password">
          Nhập lại mật khẩu
        </label>
        <input
          type="password"
          id="password"
          className="password-input-field"
          value={confirmPass}
          onChange={this.onChangeConfirmPassword}
          placeholder="Password"
        />
      </>
    )
  }

  renderUsernameField = () => {
    const {username} = this.state
    return (
      <>
        <label className="input-label" htmlFor="username">
          Tên đăng nhập
        </label>
        <input
          type="text"
          id="username"
          className="username-input-field"
          value={username}
          onChange={this.onChangeUsername}
          placeholder="Username"
        />
      </>
    )
  }

  render() {
    const {showSubmitError, errorMsg, usernameErr, passwordErr} = this.state
    const jwtToken = Cookies.get('jwt_token')
    if (jwtToken !== undefined) {
      return <Redirect to="/" />
    }
    return (
      <div className="login-form-container">
        <div className="landing-container">
          <p className="login-mobile-text">Đăng ký</p>
          <img
            src="https://res.cloudinary.com/dblomc9cr/image/upload/v1670912145/Rectangle_1457_cumwxk.png"
            className="login-image-mobile"
            alt="website login"
          />
        </div>

        <form className="form-container" onSubmit={this.submitForm}>
          <img
            src={image}
            alt="website-logo"
            className="login-website-logo-desktop-image"
          />
          <h1 className="tasty-text">Tasty</h1>
          <h1 className="login-text">Đăng ký</h1>
          <div className="input-container">{this.renderUsernameField()}</div>
          {showSubmitError && <p className="error-message">{usernameErr}</p>}
          <div className="input-container">{this.renderPasswordField()}</div>
          {showSubmitError && <p className="error-message">{passwordErr}</p>}
          <div className="input-container">{this.renderConfirmPasswordField()}</div>
          {showSubmitError && <p className="error-message">{errorMsg}</p>}
          <div className="sign-up-buttom">
            <button type="button" className="back-to-login" onClick={() => {
              const {history} = this.props
              Cookies.remove('jwt_token')
              history.push('/login')
            }}>&#10140;</button>
            <button type="submit" className="login-button">
              Đăng ký
            </button>
          </div>
        </form>
        <img
          src="https://res.cloudinary.com/nsp/image/upload/v1635305272/tastyKitchens/LoginLarge_1x_gfwe0e.jpg"
          alt="website logo"
          className="login-image-desktop"
        />
      </div>
    )
  }
}

export default Registry
