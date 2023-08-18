import {Component} from 'react'
import {AiFillStar} from 'react-icons/ai'

import './index.css'

class RestaurantFoodItem extends Component {
  state = {}

  componentDidMount() {
    const {eachFoodItem} = this.props
    const {id} = eachFoodItem
    const cartData = localStorage.getItem('cartData')
    const parsedCartData = JSON.parse(cartData)
    if (parsedCartData === null) {
      this.setState({
        isButtonClicked: false,
        itemQuantity: 0,
      })
    } else {
      const presentCartData = parsedCartData.filter(
        eachItem => eachItem.id === id,
      )
      if (presentCartData.length > 0) {
        this.setState({
          isButtonClicked: true,
          itemQuantity: presentCartData[0].quantity,
        })
      }
    }
  }

  updateLocalStorage = () => {
    const {isButtonClicked, itemQuantity} = this.state
    const {eachFoodItem} = this.props
    const {imageUrl, name, cost, id} = eachFoodItem

    const localCartData = localStorage.getItem('cartData')
    const parsedCartData = JSON.parse(localCartData)

    if (parsedCartData === null) {
      const updatedParsedCartData = []

      if (isButtonClicked === true && itemQuantity > 0) {
        const cartItem = {id, name, cost, imageUrl, quantity: itemQuantity}
        updatedParsedCartData.push(cartItem)
        localStorage.setItem('cartData', JSON.stringify(updatedParsedCartData))
      }
    } else {
      const updatedCartData = parsedCartData
      if (isButtonClicked === true) {
        const cartItem = {id, name, cost, imageUrl, quantity: itemQuantity}
        const updatedCart = updatedCartData.filter(
          eachItem => eachItem.id !== id,
        )
        updatedCart.push(cartItem)
        localStorage.setItem('cartData', JSON.stringify(updatedCart))
      } else {
        const updatedCart = updatedCartData.filter(
          eachItem => eachItem.id !== id,
        )
        localStorage.setItem('cartData', JSON.stringify(updatedCart))
      }
    }
  }

  onClickedAdd = () => {
    this.setState(
      {
        isButtonClicked: true,
        itemQuantity: 1,
      },
      this.updateLocalStorage,
    )
  }

  onMinusClicked = () => {
    const {itemQuantity} = this.state
    if (itemQuantity < 2) {
      this.setState(
        {
          itemQuantity: 0,
          isButtonClicked: false,
        },
        this.updateLocalStorage,
      )
    } else {
      this.setState(
        prev => ({
          itemQuantity: prev.itemQuantity - 1,
          isButtonClicked: true,
        }),
        this.updateLocalStorage,
      )
    }
  }

  onPlusClicked = () => {
    const {itemQuantity} = this.state
    const updatedItemQuantity = itemQuantity + 1
    this.setState({itemQuantity: updatedItemQuantity}, this.updateLocalStorage)
  }

  render() {
    const {eachFoodItem} = this.props
    // const {imageUrl, name, cost, rating} = eachFoodItem
    const imageUrl = 'data:image/jpeg;base64,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',
        name = 'Beef wellington',
        cost = 150,
        rating = 4.6
    // const {isButtonClicked, itemQuantity} = this.state
    // console.log(isButtonClicked)
    // console.log(itemQuantity)

    return (
      <li className="food-item-container">
        <img src={imageUrl} alt="" className="food-item-image" />
        <div className="food-item-details-container">
          <h1 className="food-item-name">{name}</h1>
          <p className="food-item-cost">{cost}</p>
          <div className="rating-container">
            <AiFillStar className="food-item-star" />
            <p className="food-item-rating">{rating}</p>
          </div>
          {/*{isButtonClicked && itemQuantity > 0 ? (*/}
            <div className="food-item-quantity-container">
              <button
                type="button"
                className="minus-button"
                onClick={this.onMinusClicked}
              >
                -
              </button>
              {/*<p className="item-quantity-number">{itemQuantity}</p>*/}
              <p className="item-quantity-number">12</p>
              <button
                type="button"
                className="plus-button"
                onClick={this.onPlusClicked}
              >
                +
              </button>
            </div>
          {/*) : (*/}
          {/*  <button*/}
          {/*    type="button"*/}
          {/*    className="food-item-button"*/}
          {/*    onClick={this.onClickedAdd}*/}
          {/*  >*/}
          {/*    Add*/}
          {/*  </button>*/}
          {/*)}*/}
        </div>
      </li>
    )
  }
}

export default RestaurantFoodItem
