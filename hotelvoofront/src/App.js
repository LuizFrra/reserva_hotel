import React, { Component } from 'react';
import './App.css';
import NavBar from '../src/Componentes/NavBar/NavBar';
import SearcbBar from '../src/Componentes/SearchBar/SearchBar'
import { BrowserRouter as Router, Switch, Route, Redirect } from "react-router-dom";
import Hotel from './Componentes/Hotel/Hotel';
import Voo from './Componentes/Voo/Voo';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      cityId: 0,
      redirectToHotel: false,
      redirectToFly: false,
      selRoomId: -1,
      selMonths: []
    }
    this.handleCitySelect = this.handleCitySelect.bind(this);
    this.handleHotelBook = this.handleHotelBook.bind(this);
  }

  handleCitySelect(cityId) {
    localStorage.setItem("cityId", cityId);
    this.setState({redirectToHotel: true});
  }

  handleHotelBook(roomId, months) {
    localStorage.setItem("months", months);
    this.setState({ redirectToHotel: false, redirectToFly: true, selRoomId: roomId, selMonths: months });
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

              <Route path="/voo">
                <Voo months={this.state.selMonths} />
              </Route>

              <Route path="/hotel">
                {this.state.redirectToFly && <Redirect to="/voo" />}
                <Hotel cityId={this.state.cityId} onHotelBook={this.handleHotelBook} ></Hotel>
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
