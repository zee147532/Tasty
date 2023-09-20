import Header from '../Header'

import AllPostsList from '../AllPostsList'
import Footer from '../Footer'

const Posts = () => {
    return (
        <>
            <Header />
            <AllPostsList paging={true} url={"http://localhost:8080/api/customer/posts"}/>
            <Footer />
        </>
    )
}

export default Posts
