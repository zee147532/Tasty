import {Component} from "react";
import './index.css'
import Cookies from 'js-cookie'

class Comment extends Component {
    state = {
        comments: [],
        subComments: [],
        addSub: NaN,
        addComment: '',
        addSubComment: '',
        avatar: '',
    }

    componentDidMount() {
        this.loadComment()
    }

    loadAvatar = async () => {
        console.log(1)
        const username = Cookies.get('username')
        console.log(username)
        const apiUrl = `http://localhost:8080/api/customer/${username}/avatar`
        const options = {
            method: 'GET',
        }
        const response = await fetch(apiUrl, options)
        if (response.ok) {
            console.log(response)
            const fetchedData = await response.json()
            this.setState({avatar: fetchedData.url})
        } else {
            this.setState({avatar: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSheI9UkWllIpSNbs2UdE18KLLswgDON9qzXg&usqp=CAU'})
            console.log("Load avatar error.")
        }
    }

    loadComment = async () => {
        const postsId = this.props.postsId
        const apiUrl = `http://localhost:8080/api/customer/posts/${postsId}/comment`
        const options = {
            method: 'GET',
        }
        const response = await fetch(apiUrl, options)
        if (response.ok) {
            const fetchedData = await response.json()
            const subComments = fetchedData.subComments
            const comments = fetchedData.comments
            this.setState({comments, subComments})
            this.loadAvatar()
        } else {
            alert("Gặp lỗi trong khi tải bình luận.")
        }
    }

    addComment = () => {
        const postsId = this.props.postsId
        const {addComment, comments} = this.state
        document.getElementById('comment-input').innerText = ''
        const data = {
            subComment: false,
            comment: addComment,
            postsId: parseInt(postsId)
        }
        this.saveComment(data)
    }

    addSubComment = id => {
        const postsId = this.props.postsId
        const {addSubComment} = this.state
        document.getElementById(id).innerText = ''
        const data = {
            subComment: true,
            comment: addSubComment,
            postsId: postsId,
            supperCommentId: parseInt(id)
        }
        this.saveComment(data)
    }

    saveComment = async (data) => {
        const jwtToken = Cookies.get('jwt_token')
        const apiUrl = 'http://localhost:8080/api/customer/comment'
        const options = {
            headers: {
                Authorization: `Bearer ${jwtToken}`,
                Accept: 'application/json',
                'Content-type': 'application/json',
            },
            body: JSON.stringify(data),
            method: 'POST',
        }
        await fetch(apiUrl, options)
        this.loadComment()
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

    deleteComment = async (id) => {
        const apiUrl = `http://localhost:8080/api/customer/comment/${id}`
        const options = {
            method: 'DELETE',
        }
        await fetch(apiUrl, options)
        this.loadComment()
    }

    render()
        {
            const {comments, subComments, addSub} = this.state
            const username = Cookies.get('username')

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
                                            <img src={comment.avatarUrl} alt=""/>
                                        </div>
                                        <div className="comment-content">
                                            <div className="comment-details">
                                                <h4 className="comment-name">{comment.username}</h4>
                                                <span className="comment-log">{comment.time}</span>
                                                {comment.username === username && (
                                                    <span className="material-symbols-rounded delete-button"
                                                    onClick={() => this.deleteComment(comment.id)}>delete</span>
                                                )}
                                            </div>
                                            <div className="comment-desc">
                                                <p>{comment.comment}</p>
                                            </div>
                                            <div className="comment-data">
                                                <div className="comment-reply">
                                                    <a className="reply-button"
                                                       onClick={() => this.setState({addSub: comment.id})}>Trả lời
                                                        <span
                                                            className="material-symbols-rounded reply-icon">reply</span>
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
                                                            src={subComment.avatarUrl}
                                                            alt=""/>
                                                    </div>
                                                    <div className="comment-content">
                                                        <div className="comment-details">
                                                            <h4 className="comment-name">{subComment.username}</h4>
                                                            <span className="comment-log">{subComment.time}</span>
                                                            {subComment.username === username && (
                                                                <span className="material-symbols-rounded delete-button">delete</span>
                                                            )}
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