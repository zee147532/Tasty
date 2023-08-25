import Cookies from 'js-cookie'
import {Component} from "react";
import './index.css'

class InformationPage extends Component {
    state = {
        professionList: [],
        fullName: '',
        phoneNumber: '',
        email: '',
        gender: '',
        profession: NaN,
        showSubmitError: false,
        fullNameErr: '',
        phoneNumberErr: '',
        emailErr: '',
        genderErr: '',
        professionErr: '',
    }

    onChangeFullname = event => {
        this.setState({fullName: event.target.value})
    }

    onChangePhoneNumber = event => {
        this.setState({phoneNumber: event.target.value})
    }

    onChangeEmail = event => {
        this.setState({email: event.target.value})
    }

    onChangeGender = event => {
        this.setState({gender: event.target.value})
    }

    onChangeProfession = event => {
        this.setState({profession: event.target.value})
    }

    getAllProfession = async () => {
        const url = 'http://localhost:8080/api/customer/professions'
        const options = {
            method: 'GET',
        }
        const response = await fetch(url, options)
        const data = await response.json()
        console.log(data)
        const professions = data.map(profession => ({
            id: profession.id,
            name: profession.name,
        }))
        professions.push({
            id: 0,
            name: 'Khác',
        })
        this.setState({professionList: professions})
    }

    errorFullName = msg => {
        this.setState({showSubmitError: true, fullNameErr: msg, phoneNumberErr: '', emailErr: '', genderErr: '', professionErr: ''})
    }

    errorPhoneNumber = msg => {
        this.setState({showSubmitError: true, fullNameErr: '', phoneNumberErr: msg, emailErr: '', genderErr: '', professionErr: ''})
    }

    errorEmail = msg => {
        this.setState({showSubmitError: true, fullNameErr: '', phoneNumberErr: '', emailErr: msg, genderErr: '', professionErr: ''})
    }

    errorGender = msg => {
        this.setState({showSubmitError: true, fullNameErr: '', phoneNumberErr: '', emailErr: '', genderErr: msg, professionErr: ''})
    }

    errorProfession = msg => {
        this.setState({showSubmitError: true, fullNameErr: '', phoneNumberErr: '', emailErr: '', genderErr: '', professionErr: msg})
    }

    onSubmitSuccess = email => {
        const {history} = this.props

        Cookies.set('email', email, {
            expires: 1800,
            path: '/',
        })
        history.push('/verification-code')
    }

    fillInfo = async event => {
        event.preventDefault()
        const username = Cookies.get('username')
        const {fullName, phoneNumber, email, gender, profession} = this.state
        if (fullName.length === 0) {
            this.errorFullName("Họ tên không được để trống.")
            return false
        }
        if (phoneNumber.length > 11) {
            this.errorPhoneNumber("Số điện thoại không hợp lệ.")
            return false
        }
        if (email.length === 0) {
            this.errorEmail("Email không được để trống.")
            return false
        }
        if (gender.length === 0) {
            this.errorGender("Vui lòng chọn giới tính.")
            return false
        }
        if (isNaN(profession)) {
            this.errorProfession("Vui lòng chọn nghề nghiệp.")
            return false
        }

        const userInfo = {username: username, fullName, phoneNumber, email, gender, profession}

        const url = 'http://localhost:8080/api/customer/fill-info'
        const options = {
            method: 'POST',
            body: JSON.stringify(userInfo),
            headers: {
                Accept: 'application/json',
                'Content-type': 'application/json',
            },
        }
        const response = await fetch(url, options)
        const data = await response.json()
        if (response.ok === true) {
            this.onSubmitSuccess(data.email)
        } else {
            this.errorProfession(data.errorMsg)
        }
    }

    render() {
        const {professionList, showSubmitError, fullNameErr, phoneNumberErr, emailErr, genderErr, professionErr} = this.state
        const registryUsername = Cookies.get('username')
        return (
            <>
                <div className="wrapper">
                    <form className="form_wrap" onSubmit={this.fillInfo}>
                        <div className="form_1 data_info">
                            <h2>Thông tin người dùng</h2>
                            <div className="form_container form_wrap">
                                <div className="input_wrap">
                                    <label>Tên đăng nhập</label>
                                    <input type="text" className="input" id="email" disabled value={registryUsername} />
                                </div>
                                <div className="input_wrap">
                                    <label>Họ tên <span className="require-label">*</span></label>
                                    <input type="text" className="input" onChange={this.onChangeFullname} />
                                    {showSubmitError && <p className="error-message">{fullNameErr}</p>}
                                </div>
                                <div className="input_wrap">
                                    <label>Số điện thoại</label>
                                    <input type="number" className="input" onChange={this.onChangePhoneNumber} />
                                    {showSubmitError && <p className="error-message">{phoneNumberErr}</p>}
                                </div>
                                <div className="input_wrap">
                                    <label>Email <span className="require-label">*</span></label>
                                    <input type="text" className="input" onChange={this.onChangeEmail} />
                                    {showSubmitError && <p className="error-message">{emailErr}</p>}
                                </div>
                                <div className="input_wrap">
                                    <label>Giới tính <span className="require-label">*</span></label>
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
                                    {showSubmitError && <p className="error-message">{genderErr}</p>}
                                </div>
                                <div className="input_wrap">
                                    <label>Nghề nghiệp <span className="require-label">*</span></label>
                                    <select className="input" onChange={this.onChangeProfession} onClick={this.getAllProfession} >
                                        <option value="" disabled selected hidden>Select</option>
                                        {professionList.map(profession => (
                                            <option value={profession.id}>{profession.name}</option>
                                        ))}
                                    </select>
                                    {showSubmitError && <p className="error-message">{professionErr}</p>}
                                </div>
                            </div>
                            <div className="btns_wrap">
                                <div className="common_btns form_1_btns">
                                    <button type="submit" className="btn_next">Xác thực <span className="icon"><ion-icon
                                        name="arrow-forward-sharp"></ion-icon></span></button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </>
        )
    }
}

export default InformationPage