import {Component} from "react";
import './index.css'

class AllStepEdit extends Component {
    state = {
        addValue: '',
        edit: {id: NaN, value: ''},
        allSteps: [{id: 1, content: 'Bỏ chảo vào dầu'}],
        count: 0,
    }

    /* Delete a task */
    deleteStep = (id) => {
        const {allSteps} = this.state
        let index;
        for (let x = 0; x < allSteps.length; x++) {
            if (allSteps[x].id === id) {
                index = x
                break
            }
        }
        allSteps.splice(index, 1)
        this.setState({allSteps: [...allSteps]})
    }


    /* Clear all step */
    clearAll = () => {
        this.setState({allSteps: []})
    }

    /* Add a new step to the list of steps on key press enter */
    onKeyPressAdd = (e) => {
        const {allSteps, count} = this.state
        console.log(e.target.value.length, e)
        if (e.keyCode === 13 && e.shiftKey === false && e.target.value !== '') {
            e.preventDefault()
            allSteps.push({
                "id": count + 1,
                "content": e.target.value
            })
            this.setState({allSteps: [...allSteps], addValue: '', count: count + 1})
        }
    }

    /* Edit/update step on key press enter inside of text area */
    onKeyPressEdit = (e) => {
        if (e.keyCode === 13 && e.shiftKey === false && e.target.value !== '') {
            e.preventDefault()
            this.updateTask()
        }
    }

    /* Update task */
    updateTask = () => {
        const {allSteps, edit} = this.state
        for (let x = 0; x < allSteps.length; x++) {
            if (allSteps[x].id === edit.id) {
                allSteps[x].content = edit.value;
                break;
            }
        }
        this.setState({allSteps: [...allSteps], edit: {}})
    }

    render() {
        const {allSteps, edit, addValue} = this.state

        return (
            <div className="todo-container">
                <h1>Các bước thực hiện:</h1>
                <div className="tasks-container">
                    {allSteps.map((stepItem, index) => {
                        return (
                            <div key={stepItem.id} className="task-item-container">
                                <div className="clear-resolved" onClick={this.clearAll}>
                                    Xóa tất cả
                                </div>
                                <div className="step-list">
                                    <span className="task step-index"> Bước {index + 1}:</span>
                                    <span
                                        className="step-content"> {stepItem.content}
                                    </span>
                                </div>
                                <div className="task-actions">
                                    <div className="task-icons anchor material-symbols-outlined layer-1 todo-icons"
                                         onClick={() => this.deleteStep(stepItem.id)} data-hover="Xóa">
                                        delete
                                    </div>
                                    {edit.id === stepItem.id ?
                                        <div className="task-icons anchor material-symbols-outlined layer-1 todo-icons"
                                             onClick={this.updateTask} data-hover="Lưu">
                                            save
                                        </div>
                                        :
                                        <div className="task-icons anchor material-symbols-outlined layer-1 todo-icons" onClick={() => {
                                                this.setState({edit: {
                                                    id: stepItem.id,
                                                    value: stepItem.content
                                                }})
                                            }} data-hover="Chỉnh sửa">
                                            edit
                                        </div>
                                    }
                                </div>
                                <input
                                    key={stepItem.id}
                                    className={edit.id === stepItem.id ? "display" : "none"}
                                    value={edit.value}
                                    onChange={(e) => this.setState({edit: {
                                        id: edit.id,
                                        value: e.target.value
                                    }})}
                                    onKeyDown={this.onKeyPressEdit}>
                                </input>
                            </div>
                        )
                    })}
                </div>
                <div className="add-tasks-container">
                    <div className="add-task-btn">Thêm bước:</div>
                    <input
                        placeholder="Nhấn enter để thêm bước thực hiện ..."
                        value={addValue}
                        onChange={(e) => this.setState({addValue: e.target.value})}
                        onKeyDown={this.onKeyPressAdd}>
                    </input>
                </div>
            </div>
        )
    }
}

export default AllStepEdit