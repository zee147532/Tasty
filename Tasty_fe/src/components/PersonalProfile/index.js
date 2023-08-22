import {Component} from "react";
import './index.css'
import AllRestaurantsList from "../AllRestaurantsList";

class PersonalProfile extends Component {
    render() {
        return (
            <>
                <header className="profile-header">
                    <div className="container">
                        <div className="profile">
                            <div className="profile-image">
                                <img src="https://images.unsplash.com/photo-1513721032312-6a18a42c8763?w=152&h=152&fit=crop&crop=faces" alt="" />
                            </div>
                            <div className="profile-user-settings">
                                <h1 className="profile-user-name">janedoe_</h1>
                                <button className="btn profile-edit-btn">Chỉnh sửa</button>
                                <button className="btn profile-settings-btn" aria-label="profile settings"><i className="fas fa-cog" aria-hidden="true"></i></button>
                            </div>
                            <div className="profile-stats">
                                <ul>
                                    <li><span className="profile-stat-count">164</span> bài viết</li>
                                    {/*<li><span className="profile-stat-count">188</span> followers</li>*/}
                                    {/*<li><span className="profile-stat-count">206</span> following</li>*/}
                                </ul>
                            </div>
                            <div className="profile-bio">
                                <p><span className="profile-real-name">Jane Doe</span> Lorem ipsum dolor sit, amet consectetur adipisicing elit 📷✈️🏕️</p>
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
                            <li>
                                <a href="#2b" data-toggle="tab">Yêu thích</a>
                            </li>
                            <li>
                                <a href="#3b" data-toggle="tab">Applying clearfix</a>
                            </li>
                            <li>
                                <a href="#4b" data-toggle="tab">Background color</a>
                            </li>
                        </ul>

                        <div className="tab-content clearfix">
                            <div className="tab-pane active" id="1b">
                                <AllRestaurantsList paging={false} url={"http://localhost:8080/api/customer/posts"}/>
                            </div>
                            <div className="tab-pane" id="2b">
                                <AllRestaurantsList paging={false} url={"http://localhost:8080/api/customer/posts"}/>
                            </div>
                            <div className="tab-pane" id="3b">
                                <h3>We applied clearfix to the tab-content to rid of the gap between the tab and the content</h3>
                            </div>
                            <div className="tab-pane" id="4b">
                                <h3>We use css to change the background color of the content to be equal to the tab</h3>
                            </div>
                        </div>
                    </div>
                </main>
            </>
        )
    }
}

export default PersonalProfile