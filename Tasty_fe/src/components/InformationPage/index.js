import Cookies from 'js-cookie'
import {Component} from "react";
import './index.css'
import RestaurantCard from "../RestaurantCard";

class InformationPage extends Component {
    state = {
        professionList: [],
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
        this.setState({professionList: professions})
    }

    render() {
        const {professionList} = this.state
        const registryUsername = Cookies.get('username')
        return (
            <>
                <div className="wrapper">
                    <div className="form_wrap">
                        <div className="form_1 data_info">
                            <h2>Thông tin người dùng</h2>
                            <form>
                                <div className="form_container">
                                    <div className="input_wrap">
                                        <label>Tên đăng nhập</label>
                                        <input type="text" name="Email Address" className="input" id="email" disabled value={registryUsername} />
                                    </div>
                                    <div className="input_wrap">
                                        <label>Họ tên</label>
                                        <input type="text" name="fullName" className="input" />
                                    </div>
                                    <div className="input_wrap">
                                        <label>Số điện thoại</label>
                                        <input type="number" name="phoneNumber" className="input"  />
                                    </div>
                                    <div className="input_wrap">
                                        <label>Email</label>
                                        <input type="text" name="email" className="input" />
                                    </div>
                                    <div className="input_wrap">
                                        <label>Giới tính</label>
                                        <div>
                                            <input type="radio" name="gender" className="gender-radio" value="NU" />Nữ
                                            <input type="radio" name="gender" className="gender-radio" value="NAM" />Nam
                                            <input type="radio" name="gender" className="gender-radio" value="AN" />Bí mật
                                        </div>
                                    </div>
                                    <div className="input_wrap">
                                        <label>Nghề nghiệp</label>
                                        <select name="professionId" className="input" onClick={() => {
                                            this.getAllProfession()
                                        }} >
                                            <option value="" disabled selected hidden>Select</option>
                                            {professionList.map(profession => (
                                                <option value={profession.id}>{profession.name}</option>
                                            ))}
                                            <option value="0">Khác</option>
                                        </select>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div className="btns_wrap">
                        <div className="common_btns form_1_btns">
                            <button type="button" className="btn_next">Xác thực <span className="icon"><ion-icon
                                name="arrow-forward-sharp"></ion-icon></span></button>
                        </div>
                    </div>
                </div>

                <div className="modal_wrapper">
                    <div className="shadow"></div>
                    <div className="success_wrap">
                        <span className="modal_icon"><ion-icon name="checkmark-sharp"></ion-icon></span>
                        <p>You have successfully completed the process.</p>
                    </div>
                </div>
            </>
        )
    }
}

export default InformationPage