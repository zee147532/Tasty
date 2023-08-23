import {Component} from "react";
import './index.css'

class Comment extends Component {
    state = {
        comments: [
            {id: 1, username: 'abc', comment: '123456789', time: '12 phút trước'},
            {id: 2, username: 'efg', comment: '10111213141516171819', time: '2 giờ trước'},
            {id: 3, username: 'hij', comment: '20212223242526272829', time: '5 giờ trước'},
            {id: 4, username: 'klm', comment: '30313233343536373839', time: '8 giờ trước'},
            {id: 5, username: 'nop', comment: '40414243444546474849', time: '20 giờ trước'},
            {id: 6, username: 'qrs', comment: '50515253545556575859', time: '2 ngày trước'},
        ],
        subComments: [
            {id: 1, username: 'abc', comment: '123456789', superComment: 1, time: 'Vừa xong'},
            {id: 2, username: 'efg', comment: '10111213141516171819', superComment: 1, time: '30 phút trước'},
            {id: 3, username: 'hij', comment: '20212223242526272829', superComment: 1, time: '12 giờ trước'},
            {id: 4, username: 'klm', comment: '30313233343536373839', superComment: 2, time: '1 ngày trước'},
            {id: 5, username: 'nop', comment: '40414243444546474849', superComment: 2, time: '1 tháng trước'},
            {id: 6, username: 'qrs', comment: '50515253545556575859', superComment: 3, time: '1 năm trước'},
        ],
        addSub: NaN,
        addComment: '',
        addSubComment: '',
    }

    addComment = () => {
        const {addComment, comments} = this.state
        const maxId = Math.max(...comments.map(c => c.id))
        comments.unshift({id: maxId + 1, comment: addComment, time: 'Vừa xong', username: 'xyz'})
        this.setState({comments})
        document.getElementById('comment-input').innerText = ''
    }

    addSubComment = id => {
        const {addSubComment, subComments} = this.state
        const maxId = Math.max(...subComments.map(c => c.id))
        subComments.unshift({
            id: maxId + 1,
            comment: addSubComment,
            time: 'Vừa xong',
            username: 'xyz',
            superComment: id
        })
        this.setState({subComments})
        document.getElementById(id).innerText = ''
    }

    onKeyDownComment = (e) => {
        if (e.keyCode === 13 && e.shiftKey === false && e.currentTarget.innerText !== '') {
            e.preventDefault()
            this.addComment()
        }
        this.setState({addComment: e.currentTarget.innerText})
    }

    onKeyDownSubComment = (e) => {
        const {addSub} = this.state
        if (e.keyCode === 13 && e.shiftKey === false && e.currentTarget.innerText !== '') {
            e.preventDefault()
            this.addSubComment(addSub)
        }
        this.setState({addSubComment: e.currentTarget.innerText})
    }

    render() {
        const {comments, subComments, addSub} = this.state

        return (
            <div className="comment-container">
                <div className="input-comment">
                    <p className="comment-title">Bình luận:</p>
                    <div className="comment-action">
                        <span id="comment-input" role="textbox" contentEditable className="comment-input"
                              onKeyDown={this.onKeyDownComment}/>
                        <button className='primaryContained float-right' type="submit" onClick={this.addComment}>
                            <span className="material-symbols-rounded">send</span>
                        </button>
                    </div>
                </div>
                <section className="comment-module">
                    <ul className="list-comments">
                        {comments.map(comment => (
                            <li>
                                <div className="comment">
                                    <div className="comment-img">
                                        <img src="https://rvs-comment-module.vercel.app/Assets/User Avatar.png" alt=""/>
                                    </div>
                                    <div className="comment-content">
                                        <div className="comment-details">
                                            <h4 className="comment-name">{comment.username}</h4>
                                            <span className="comment-log">{comment.time}</span>
                                        </div>
                                        <div className="comment-desc">
                                            <p>{comment.comment}</p>
                                        </div>
                                        <div className="comment-data">
                                            <div className="comment-reply">
                                                <a className="reply-button"
                                                   onClick={() => this.setState({addSub: comment.id})}>Trả lời
                                                    <span className="material-symbols-rounded reply-icon">reply</span>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <ul className="list-comments">
                                    <div className={`input-sub-comment ${addSub === comment.id ? '' : 'disable'}`}>
                                        <span role="textbox" contentEditable id={comment.id} className="comment-input"
                                              onKeyDown={this.onKeyDownSubComment}/>
                                        <button className='primaryContained float-right' type="submit"
                                                onClick={() => this.addSubComment(comment.id)}>
                                            <span className="material-symbols-rounded">send</span>
                                        </button>
                                    </div>
                                    {subComments.filter(sc => {
                                        return sc.superComment === comment.id
                                    }).map(subComment => (
                                        <li>
                                            <div className="comment">
                                                <div className="comment-img">
                                                    <img
                                                        src="https://rvs-comment-module.vercel.app/Assets/User Avatar-1.png"
                                                        alt=""/>
                                                </div>
                                                <div className="comment-content">
                                                    <div className="comment-details">
                                                        <h4 className="comment-name">{subComment.username}</h4>
                                                        <span className="comment-log">{subComment.time}</span>
                                                    </div>
                                                    <div className="comment-desc">
                                                        <p>{subComment.comment}</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    ))}
                                </ul>
                            </li>
                        ))}
                    </ul>
                </section>
            </div>
        )
    }
}

export default Comment