import {BsFilterRight} from 'react-icons/bs'

import './index.css'
import {useState} from "react";

const RestaurantHeader = props => {
  const onChangeSortBy = event => {
    const {changeSortBy} = props
    changeSortBy(event.target.value)
  }

  const changeKeyword = e => {
    const {onChangeKeyword}  = props
    onChangeKeyword(e.target.value)
  }

  const keyPressSearch = e => {
    const {search} = props
    if (e.keyCode === 13 && e.shiftKey === false) {
      e.preventDefault()
      search()
    }
  }

  const clickSearch = (e) => {
    const {search} = props
    search()
  }

  const [isOpen, setIsOpen] = useState(false);

  const {sortByOptions, activeOptionId} = props
  return (
    <div className="restaurant-header">
        <div className="header-text-container">
          <h1 className="restaurant-list-heading">Công thức mới nhất</h1>
          <p className="restaurant-list-para">
            Lựa chọn món ăn mà bạn yêu thích và tạo nên những bữa ăn tuyệt vời...
          </p>
        </div>
        <div className="sort-by-container">
          <BsFilterRight className="sort-by-icon" />
          <p className="sort-by">Sắp xếp</p>
          <select
            className="sort-by-select"
            value={activeOptionId}
            onChange={onChangeSortBy}
          >
            {sortByOptions.map(eachOption => (
              <option
                key={eachOption.id}
                value={eachOption.value}
                className="select-option"
              >
                {eachOption.displayText}
              </option>
            ))}
          </select>
          <div className={`input-box ${isOpen ? "open" : ""}`}>
            <input type="text" placeholder="Search..." onChange={changeKeyword} onKeyDown={keyPressSearch} />
            <span className="search">
            <i className="uil uil-search search-icon" onClick={() => {
              if (isOpen) {
                clickSearch()
              } else {
                setIsOpen(true);
              }
            }}></i>
          </span>
            <i className="uil uil-times close-icon" onClick={() => {
              setIsOpen(false);
            }}></i>
          </div>
        </div>
    </div>
  )
}

export default RestaurantHeader
