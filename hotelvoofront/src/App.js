import React, { Component } from 'react';
import './App.css';
import NavBar from '../src/Componentes/NavBar/NavBar';
import SearcbBar from '../src/Componentes/SearchBar/SearchBar'
import { BrowserRouter as Router, Switch, Route, Redirect } from "react-router-dom";
import Hotel from './Componentes/Hotel/Hotel';
import Voo from './Componentes/Voo/Voo';
import Result from './Componentes/ResultPage/ResultPage';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      cityId: 0,
      redirectToHotel: false,
      redirectToFly: false,
      redirectToResult: false,
      selRoomId: -1,
      selMonths: [],
      selFlight: ""
    }
    this.handleCitySelect = this.handleCitySelect.bind(this);
    this.handleHotelBook = this.handleHotelBook.bind(this);
    this.handleFlightSelect = this.handleFlightSelect.bind(this);
  }

  handleFlightSelect(flight) {
    localStorage.setItem("flight", flight);
    this.setState({ redirectToHotel: false, redirectToFly: false,  selFlight: flight, redirectToResult: true });
  }

  handleCitySelect(cityId) {
    localStorage.setItem("cityId", cityId);
    this.setState({redirectToHotel: true});
  }

  handleHotelBook(roomId, months) {
    localStorage.setItem("months", months);
    localStorage.setItem("room", roomId);
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

              <Route path="/result">
                <Result months={this.state.selMonths} room={this.state.selRoomId} flight={this.state.selFlight}>

                </Result>
              </Route>

              <Route path="/voo">
                {this.state.redirectToResult && <Redirect to="/result" />}
                <Voo months={this.state.selMonths} onFLightSel={this.handleFlightSelect}/>
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
