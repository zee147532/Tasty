import {Component} from "react";
import './index.css'

class Rating extends Component {
    state = {
        starClicked: false,
    }

    render() {
        return (
            <>
            <div className="rating" data-vote="0">

                <div className="star hidden">
                    <span className="full" data-value="0"></span>
                    <span className="half" data-value="0"></span>
                </div>

                <div className="star">

                    <span className="full" data-value="1"></span>
                    <span className="half" data-value="0.5"></span>
                    <span className="selected"></span>

                </div>

                <div className="star">

                    <span className="full" data-value="2"></span>
                    <span className="half" data-value="1.5"></span>
                    <span className="selected"></span>

                </div>

                <div className="star">

                    <span className="full" data-value="3"></span>
                    <span className="half" data-value="2.5"></span>
                    <span className="selected"></span>

                </div>

                <div className="star">

                    <span className="full" data-value="4"></span>
                    <span className="half" data-value="3.5"></span>
                    <span className="selected"></span>

                </div>

                <div className="star">

                    <span className="full" data-value="5"></span>
                    <span className="half" data-value="4.5"></span>
                    <span className="selected"></span>

                </div>

                <div className="score">
                    <span className="score-rating js-score">0</span>
                    <span>/</span>
                    <span className="total">5</span>
                </div>
            </div>

        <div className="rating" data-vote="0">

            <div className="star hidden">
                <span className="full" data-value="0"></span>
                <span className="half" data-value="0"></span>
            </div>

            <div className="star">

                <span className="full" data-value="1"></span>
                <span className="half" data-value="0.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="2"></span>
                <span className="half" data-value="1.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="3"></span>
                <span className="half" data-value="2.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="4"></span>
                <span className="half" data-value="3.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="5"></span>
                <span className="half" data-value="4.5"></span>
                <span className="selected"></span>

            </div>

            <div className="score">
                <span className="score-rating js-score">0</span>
                <span>/</span>
                <span className="total">5</span>
            </div>
        </div>

        <div className="rating"  data-vote="0" >

            <div className="star hidden">
                <span className="full" data-value="0"></span>
                <span className="half" data-value="0"></span>
            </div>

            <div className="star">

                <span className="full" data-value="1"></span>
                <span className="half" data-value="0.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="2"></span>
                <span className="half" data-value="1.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="3"></span>
                <span className="half" data-value="2.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="4"></span>
                <span className="half" data-value="3.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="5"></span>
                <span className="half" data-value="4.5"></span>
                <span className="selected"></span>

            </div>

            <div className="score">
                <span className="score-rating js-score">0</span>
                <span>/</span>
                <span className="total">5</span>
            </div>
        </div>

        <div className="rating"  data-vote="0">

            <div className="star hidden">
                <span className="full" data-value="0"></span>
                <span className="half" data-value="0"></span>
            </div>

            <div className="star">

                <span className="full" data-value="1"></span>
                <span className="half" data-value="0.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="2"></span>
                <span className="half" data-value="1.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="3"></span>
                <span className="half" data-value="2.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="4"></span>
                <span className="half" data-value="3.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="5"></span>
                <span className="half" data-value="4.5"></span>
                <span className="selected"></span>

            </div>

            <div className="score">
                <span className="score-rating js-score">0</span>
                <span>/</span>
                <span className="total">5</span>
            </div>
        </div>

        <div className="rating"  data-vote="0">

            <div className="star hidden">
                <span className="full" data-value="0"></span>
                <span className="half" data-value="0"></span>
            </div>

            <div className="star">

                <span className="full" data-value="1"></span>
                <span className="half" data-value="0.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="2"></span>
                <span className="half" data-value="1.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="3"></span>
                <span className="half" data-value="2.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="4"></span>
                <span className="half" data-value="3.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="5"></span>
                <span className="half" data-value="4.5"></span>
                <span className="selected"></span>

            </div>

            <div className="score">
                <span className="score-rating js-score">0</span>
                <span>/</span>
                <span className="total">5</span>
            </div>
        </div>

        <div className="rating"  data-vote="0">

            <div className="star hidden">
                <span className="full" data-value="0"></span>
                <span className="half" data-value="0"></span>
            </div>

            <div className="star">

                <span className="full" data-value="1"></span>
                <span className="half" data-value="0.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="2"></span>
                <span className="half" data-value="1.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="3"></span>
                <span className="half" data-value="2.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="4"></span>
                <span className="half" data-value="3.5"></span>
                <span className="selected"></span>

            </div>

            <div className="star">

                <span className="full" data-value="5"></span>
                <span className="half" data-value="4.5"></span>
                <span className="selected"></span>

            </div>

            <div className="score">
                <span className="score-rating js-score">0</span>
                <span>/</span>
                <span className="total">5</span>
            </div>
        </div>

        <div className="average">
            <span className="text">Your average score is</span><div className=" score-average js-average"></div>
        </div>
            </>
    )
    }
}

export default Rating