import {Component} from "react";
import './index.css'

class VerificationCode extends Component {

    render() {
        return (
            <form action="#">
                <h4 className="text-center mb-4">Enter your code</h4>
                <div className="d-flex mb-3">
                    <input className="verification-code" placeholder="______" maxLength={6} />
                </div>
                <button type="submit" className="verification-button">Verify account</button>
            </form>
        )
    }
}

export default VerificationCode