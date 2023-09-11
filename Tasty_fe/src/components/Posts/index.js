import Header from '../Header'

import AllRestaurantsList from '../AllRestaurantsList'
import Footer from '../Footer'

const Posts = () => {
    return (
        <>
            <Header />
            <AllRestaurantsList paging={true} url={"http://localhost:8080/api/customer/posts"}/>
            <Footer />
        </>
    )
}

export default Posts
