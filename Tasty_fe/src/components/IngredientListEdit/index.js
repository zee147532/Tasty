import {Component} from "react";
import './index.css'

class IngredientListEdit extends Component {
    state = {
        addValue: '',
        addUnit: NaN,
        addQuantity: NaN,
        allIngredients: this.props.ingredients,
        count: 0,
        units: [{id: 1, name: 'Củ'}, {id: 2, name: 'Quả'}, {id: 3, name: 'gram'}, {id: 4, name: 'kilogram'}],
        editable: true,
    }

    /* Delete a task */
    deleteIngredient = (id) => {
        const {allIngredients} = this.state
        let index;
        for (let x = 0; x < allIngredients.length; x++) {
            if (allIngredients[x].id === id) {
                index = x
                break
            }
        }
        allIngredients.splice(index, 1)
        this.setState({allIngredients: [...allIngredients]})
    }


    /* Clear all step */
    clearAll = () => {
        this.setState({allIngredients: []})
    }

    /* Add a new step to the list of steps on key press enter */
    addIngredient = (e) => {
        const {allIngredients, units, addValue, addQuantity, addUnit, count} = this.state
        const unit = units.find((item) => {
            return item.id == addUnit
        })
        if (addValue.length === 0) {
            document.getElementsByClassName('add-name').item(0).classList.add("warning")
            return;
        }
        if (!unit) {
            document.getElementsByClassName('add-unit').item(0).classList.add("warning")
            return
        }
        if (addQuantity < 1 || isNaN(addQuantity)) {
            document.getElementsByClassName('add-quantity').item(0).classList.add("warning")
            return;
        }
        e.preventDefault()
        allIngredients.push({id: count + 1,
            name: addValue,
            quantity: addQuantity,
            unit: unit.name,
        })
        this.setState({allIngredients: [...allIngredients], addValue: '', count: count + 1})
    }

    render() {
        const {allIngredients, addValue, units, editable} = this.state
        return (
            <div className="ingredient-container">
                <h1>Nguyên liệu:</h1>
                <div className={`add-ingredient-container ${editable ? '' : ' disable'}`}>
                    <input className="add-name"
                        placeholder="Tên nguyên liệu"
                        value={addValue}
                        onChange={(e) => {
                            this.setState({addValue: e.target.value})
                            e.target.classList.remove("warning")
                        }}>
                    </input>
                    <select className="add-unit"
                        onChange={(e) => {
                            this.setState({addUnit: e.target.value})
                            e.target.classList.remove("warning")
                        }}>
                        <option value="0" disabled selected hidden>Đơn vị</option>
                        {units.map(unit => (
                            <option value={unit.id}>{unit.name}</option>
                        ))}
                    </select>
                    <input type="number" className="add-quantity" placeholder="Số lượng"
                           onChange={(e) => {
                               this.setState({addQuantity: e.target.value})
                               e.target.classList.remove("warning")
                           }} />
                </div>
                <div className={`add-button ${editable ? '' : ' disable'}`} onClick={this.addIngredient}>
                    Thêm<span className="add-icon material-symbols-outlined">south</span>
                </div>
                <div className="ingredients-container">
                    {allIngredients.map(ingredient => {
                        return (
                            <div key={ingredient.id} className="task-item-container">
                                <div className={`clear-resolved ${editable ? '' : ' disable'}`} onClick={this.clearAll}>
                                    Xóa tất cả
                                </div>
                                <div className="ingredient-list">
                                    <span
                                        className="ingredient-content"> {ingredient.name} ({ingredient.quantity} {ingredient.unit.toLowerCase()})
                                    </span>
                                </div>
                                <div className={`ingredient-actions ${editable ? '' : ' disable'}`}>
                                    <div className="remove-icons"
                                         onClick={() => this.deleteIngredient(ingredient.id)}>
                                        <span className="remove-icon material-symbols-outlined">close</span>
                                    </div>
                                </div>
                            </div>
                        )
                    })}
                </div>
            </div>
        )
    }
}

export default IngredientListEdit