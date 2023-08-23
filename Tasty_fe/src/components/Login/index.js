import {Component} from 'react'
import Cookies from 'js-cookie'
import {Redirect} from 'react-router-dom'
import image from './logo.png'

import './index.css'

class Login extends Component {
  state = {
    username: '',
    password: '',
    showSubmitError: false,
    errorMsg: '',
  }

  onChangeUsername = event => {
    this.setState({username: event.target.value})
  }

  onChangePassword = event => {
    this.setState({password: event.target.value})
  }

  onSubmitSuccess = data => {
    const {history} = this.props

    Cookies.set('jwt_token', data.jwtToken, {
      maxage: 14400,
      path: '/',
    })
    Cookies.set('username', data.username, {
      maxage: 14400,
      path: '/',
    })
    history.push('/')
  }

  onSubmitFailure = errorMsg => {
    this.setState({showSubmitError: true, errorMsg})
  }

  submitForm = async event => {
    event.preventDefault()
    const {username, password} = this.state
    const userDetails = {username, password}
    const url = 'http://localhost:8080/api/login'
    const options = {
      method: 'POST',
      body: JSON.stringify(userDetails),
      headers: {
        Accept: 'application/json',
        'Content-type': 'application/json',
      },
    }
    const response = await fetch(url, options)
    console.log(response)
    const data = await response.json()
    if (response.ok === true) {
      this.onSubmitSuccess(data)
    } else {
      this.onSubmitFailure(data.errorMsg)
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
    const {showSubmitError, errorMsg} = this.state
    const jwtToken = Cookies.get('jwt_token')
    if (jwtToken !== undefined) {
      return <Redirect to="/" />
    }
    return (
      <div className="login-form-container">
        <div className="landing-container">
          <p className="login-mobile-text">Đăng nhập</p>
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
          <h1 className="login-text">Đăng nhập</h1>
          <div className="input-container">{this.renderUsernameField()}</div>
          <div className="input-container">{this.renderPasswordField()}</div>
          {showSubmitError && <p className="error-message">{errorMsg}</p>}
          <button type="submit" className="login-button">
            Đăng nhập
          </button>
          <a className="registry-button" onClick={() => {
            const {history} = this.props
            history.push('/registry')
          }}>Đăng ký</a>
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

export default Login
