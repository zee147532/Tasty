import Cookies from 'js-cookie'
import {Component} from "react";

class InformationPage extends Component {

    render() {
        return (
            <>
                <div className="wrapper">
                    <div className="header">
                        <ul>
                            <li className="active form_1_progessbar">
                                <div>
                                    <p>1</p>
                                </div>
                            </li>
                            <li className="form_2_progessbar">
                                <div>
                                    <p>2</p>
                                </div>
                            </li>
                            <li className="form_3_progessbar">
                                <div>
                                    <p>3</p>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div className="form_wrap">
                        <div className="form_1 data_info">
                            <h2>Signup Info</h2>
                            <form>
                                <div className="form_container">
                                    <div className="input_wrap">
                                        <label htmlFor="email">Email Address</label>
                                        <input type="text" name="Email Address" className="input" id="email" />
                                    </div>
                                    <div className="input_wrap">
                                        <label htmlFor="password">Password</label>
                                        <input type="password" name="password" className="input" id="password" />
                                    </div>
                                    <div className="input_wrap">
                                        <label htmlFor="confirm_password">Confirm Password</label>
                                        <input type="password" name="confirm password" className="input"
                                               id="confirm_password" />
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div className="form_2 data_info" style="display: none;">
                            <h2>Create user</h2>
                            <form>
                                <div className="form_container">
                                    <div className="input_wrap">
                                        <label htmlFor="user_name">User Name</label>
                                        <input type="text" name="User Name" className="input" id="user_name" />
                                    </div>
                                    <div className="input_wrap">
                                        <label htmlFor="first_name">First Name</label>
                                        <input type="text" name="First Name" className="input" id="first_name" />
                                    </div>
                                    <div className="input_wrap">
                                        <label htmlFor="last_name">Last Name</label>
                                        <input type="text" name="Last Name" className="input" id="last_name" />
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div className="form_3 data_info" style="display: none;">
                            <h2>Professional Info</h2>
                            <form>
                                <div className="form_container">
                                    <div className="input_wrap">
                                        <label htmlFor="company">Current Company</label>
                                        <input type="text" name="Current Company" className="input" id="company" />
                                    </div>
                                    <div className="input_wrap">
                                        <label htmlFor="experience">Total Experience</label>
                                        <input type="text" name="Total Experience" className="input" id="experience" />
                                    </div>
                                    <div className="input_wrap">
                                        <label htmlFor="designation">Designation</label>
                                        <input type="text" name="Designation" className="input" id="designation" />
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div className="btns_wrap">
                        <div className="common_btns form_1_btns">
                            <button type="button" className="btn_next">Next <span className="icon"><ion-icon
                                name="arrow-forward-sharp"></ion-icon></span></button>
                        </div>
                        <div className="common_btns form_2_btns" style="display: none;">
                            <button type="button" className="btn_back"><span className="icon"><ion-icon
                                name="arrow-back-sharp"></ion-icon></span>Back
                            </button>
                            <button type="button" className="btn_next">Next <span className="icon"><ion-icon
                                name="arrow-forward-sharp"></ion-icon></span></button>
                        </div>
                        <div className="common_btns form_3_btns" style="display: none;">
                            <button type="button" className="btn_back"><span className="icon"><ion-icon
                                name="arrow-back-sharp"></ion-icon></span>Back
                            </button>
                            <button type="button" className="btn_done">Done</button>
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