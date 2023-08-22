import {Component} from "react";
import './index.css'

class AllStepEdit extends Component {
    state = {
        addValue: '',
        edit: {id: NaN, value: ''},
    }

    /* Delete a step */
    deleteStep = (id) => {
        this.props.onDelete(id)
    }

    /* Clear all step */
    clearAll = () => {
        this.setState({allSteps: []})
    }

    /* Add a new step to the list of steps on key press enter */
    onKeyPressAdd = (e) => {
        console.log(e.target.value.length, e)
        if (e.keyCode === 13 && e.shiftKey === false && e.target.value !== '') {
            e.preventDefault()
            const id = Math.max(...this.props.steps.map(i => i.id))
            const item = {
                "id": id + 1,
                "content": e.target.value
            }
            this.props.onAdd(item)
            this.setState({addValue: ''})
        }
    }

    /* Edit/update step on key press enter inside of text area */
    onKeyPressEdit = (e) => {
        if (e.keyCode === 13 && e.shiftKey === false && e.target.value !== '') {
            e.preventDefault()
            this.updateStep()
        }
    }

    /* Update task */
    updateStep = () => {
        const {edit} = this.state
        this.props.onUpdate(edit)
        this.setState({edit: {}})
    }

    render() {
        const {edit, addValue} = this.state
        const editable = this.props.editable

        return (
            <div className="todo-container">
                <h1>Các bước thực hiện:</h1>
                <div className="steps-container">
                    {this.props.steps.map((stepItem, index) => {
                        return (
                            <div key={stepItem.id} className="ingredient-item-container">
                                <div className={`clear-resolved ${editable ? '' : ' disable'}`} onClick={this.clearAll}>
                                    Xóa tất cả
                                </div>
                                <div className={`step-list ${editable ? 'step-width' : ''}`}>
                                    <span className="task step-index"> Bước {index + 1}:</span>
                                    <span
                                        className="step-content"> {stepItem.content}
                                    </span>
                                </div>
                                <div className={`step-actions ${editable ? '' : ' disable'}`}>
                                    <div className="task-icons anchor material-symbols-outlined layer-1 todo-icons"
                                         onClick={() => this.deleteStep(stepItem.id)} data-hover="Xóa">
                                        delete
                                    </div>
                                    {edit.id === stepItem.id ?
                                        <div className="task-icons anchor material-symbols-outlined layer-1 todo-icons"
                                             onClick={this.updateStep} data-hover="Lưu">
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
                                    className={edit.id === stepItem.id ? "display" : "none" }
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
                <div className={`add-step-container ${editable ? '' : ' disable'}`}>
                    <div className="add-step-btn">Thêm bước:</div>
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