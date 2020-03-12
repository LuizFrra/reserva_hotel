import React, { Component } from 'react';
import './App.css';
import NavBar from '../src/Componentes/NavBar/NavBar';
import SearcbBar from '../src/Componentes/SearchBar/SearchBar'
import { BrowserRouter as Router, Switch, Route, Redirect } from "react-router-dom";
import Hotel from './Componentes/Hotel/Hotel';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      cityId: 0,
      redirectToHotel: false
    }
    this.handleCitySelect = this.handleCitySelect.bind(this);
  }

  handleCitySelect(cityId) {
    this.setState({ cityId: cityId }, () => console.log(this.state.cityId));
    this.setState({redirectToHotel: true});
  }


  render() {
    return (
      <Router>
        <React.Fragment>
          <div className={"divNavBar"}>
            <NavBar></NavBar>
          </div>

          <div className={"content"}>

            <Switch>

              <Route path="/hotel">
                <Hotel cityId={this.state.cityId}></Hotel>
              </Route>

              <Route path="/">
                {this.state.redirectToHotel && <Redirect to="/hotel" />}
                <SearcbBar onCitySelect={this.handleCitySelect}></SearcbBar>
              </Route>

            </Switch>

          </div>
        </React.Fragment>
      </Router>
    );
  }
}

export default App;
