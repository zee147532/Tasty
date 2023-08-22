import {Redirect} from 'react-router-dom'
import Cookies from 'js-cookie'
import Header from '../Header'

import AllRestaurantsList from '../AllRestaurantsList'
import Cart from '../Cart'
import Footer from '../Footer'

import './index.css'

const Home = () => {
  // const jwtToken = Cookies.get('jwt_token')
  // if (jwtToken === undefined) {
  //   return <Redirect to="/login" />
  // }
  return (
    <>
      <Header />
      <AllRestaurantsList paging={true} url={"http://localhost:8080/api/customer/posts"}/>
      <Footer />
    </>
  )
}

export default Home
