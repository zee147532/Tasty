import {Component} from "react";
import './index.css'

class IngredientListEdit extends Component {
    state = {
        addValue: '',
        addUnit: NaN,
        addQuantity: NaN,
        allIngredients: [{id: 1, name: 'Bỏ chảo vào dầu', quantity: 0, unit: 'gram'}],
        count: 0,
        units: [{id: 1, name: 'Củ'}, {id: 2, name: 'Quả'}, {id: 3, name: 'gram'}, {id: 4, name: 'kilogram'}]
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
    onKeyPressAdd = (e) => {
        const {allIngredients, units, addQuantity, addUnit, count} = this.state
        if (isNaN(addUnit)) {
            return
        }
        let unit
        for (let x = 0; x < units.length; x++) {
            if (units[x].id === addUnit) {
                unit = units[x]
            }
        }
        // const unit = units.find((item) => {
        //     return item.id === addUnit
        // })
        console.log(unit)
        if (e.keyCode === 13 && e.shiftKey === false && e.target.value !== '') {
            e.preventDefault()
            allIngredients.push({
                id: count + 1,
                name: e.target.value,
                quantity: addQuantity,
                unit: 2,
            })
            this.setState({allIngredients: [...allIngredients], addValue: '', count: count + 1})
        }
    }

    render() {
        const {allIngredients, addValue, units} = this.state

        return (
            <div className="ingredient-container">
                <h1>Nguyên liệu:</h1>
                <div className="add-ingredient-container">
                    <input className="add-name"
                        placeholder="Tên nguyên liệu"
                        value={addValue}
                        onChange={(e) => this.setState({addValue: e.target.value})}
                        onKeyDown={this.onKeyPressAdd}>
                    </input>
                    <select className="add-unit"
                    onChange={(e) => this.setState({addUnit: e.target.value})}>
                        <option value="0" disabled selected hidden>Đơn vị</option>
                        {units.map(unit => (
                            <option value={unit.id}>{unit.name}</option>
                        ))}
                    </select>
                    <input type="number" className="add-quantity" placeholder="Số lượng"
                           onChange={(e) => this.setState({addQuantity: e.target.value})} />
                </div>
                <div className="ingredients-container">
                    {allIngredients .map(ingredient => {
                        return (
                            <div key={ingredient.id} className="task-item-container">
                                <div className="clear-resolved" onClick={this.clearAll}>
                                    Xóa tất cả
                                </div>
                                <div className="ingredient-list">
                                    <span
                                        className="ingredient-content"> {ingredient.name} ({ingredient.quantity} {ingredient.unit.toLowerCase()})
                                    </span>
                                </div>
                                <div className="ingredient-actions">
                                    <div className="remove-icons"
                                         onClick={() => this.deleteIngredient(ingredient.id)}>
                                        x
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