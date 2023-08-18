import {Switch, Redirect, Route} from 'react-router-dom'

import ProtectedRoute from './components/ProtectedRoute'

import Login from './components/Login'

import Home from './components/Home'
import RestaurantDetails from './components/RestaurantDetails'
import Cart from './components/Cart'
import NotFound from './components/NotFound'
import Registry from './components/Registry'
import InformationPage from "./components/InformationPage";
import VerificationCode from "./components/VerificationCode";
import RegistrySuccess from './components/RegistrySuccess'

import './App.css'

const App = () => (
  <Switch>
    <Route exact path="/login" component={Login} />
    <Route exact path="/registry" component={Registry} />
    <Route exact path="/info-page" component={InformationPage} />
    <Route exact path="/verification-code" component={VerificationCode} />
    <Route exact path="/registry/success" component={RegistrySuccess} />
    <Route exact path="/" component={Home} />
    <Route
      exact
      path="/restaurant/:id"
      component={RestaurantDetails}
    />
    <ProtectedRoute exact path="/cart" component={Cart} />
    <Route path="/bad-path" component={NotFound} />
    <Redirect to="bad-path" />
  </Switch>
)

export default App
