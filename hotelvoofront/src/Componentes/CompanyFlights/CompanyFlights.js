import React, { Component } from 'react'
import { Card, Button, notification } from 'antd'
import { BookOutlined, SmileOutlined, FrownOutlined } from '@ant-design/icons';
import monthsUtil from './months';

const gridStyle = {
    width: '100%',
    textAlign: 'center',
};

const gridCenter = {
    textAlign: 'center'
}

export default class CompanyFlights extends Component {
    
    constructor(props){
        super(props);
        this.state =  { flights:[], loading: true };
        this.handleFlightClick = this.handleFlightClick.bind(this);
    }

    openNotification(isValid) {
        if(isValid) {
            notification.open({
                message: 'Avião Disponível',
                description:
                "Este Voo é valido, pode proseguir com a compra.",
                icon: <SmileOutlined style={{ color: '#108ee9' }} />,
            });
        }else {
            notification.open({
                message: 'Voo Inválido',
                description:
                "Este Voo está com o máximo de reserva para um ou todos meses solicitados.",
                icon: <FrownOutlined style={{ color: '#108ee9' }} />,
            });
        }
    }

    validateFlight(e) {
        var monthsCount = new Array(12).fill(0);
        const flightId = e.target.dataset.index;
        const flights = this.props.flights;
        const selMonths = Array.from(localStorage.getItem("months").split(','), Number);
        const selectedFlight = flights.find(x => x.id === flightId);
        const flightBook = selectedFlight.flightBook;
        const maxSeat = selectedFlight.seat;
        const months = Object.assign(monthsUtil);

        for(var item in Object.entries(flightBook)){
            monthsCount[months[flightBook[item].month]]++;
        }

        var isValid = true;
        for(var month in selMonths) {
            isValid = isValid && (maxSeat > monthsCount[selMonths[month]]);
        }

        this.openNotification(isValid);
        return isValid;
    }

    handleFlightClick(e) {
        //this.props.onClick(e.target.dataset.index);
        if(this.validateFlight(e)) {
            this.props.onClick(e.target.dataset.index, false);
        } else {
            this.props.onClick(-1, true);
        }
    }

    componentDidMount() {
        const flights = this.props.flights;
        const months = this.props.months;
        
        var flightsGrid = [];
        for(var flight in flights) {
            flightsGrid.push(
                <Card.Grid style={gridCenter} 
                    key={flight}>{"Voo " + flight}
                    <Button style={{marginLeft: 10}} onClick={this.handleFlightClick} type="dashed" shape="circle"
                    data-index={flights[flight].id} icon={<BookOutlined/>} /> 
                </Card.Grid>
            );
        }
        
        if(flightsGrid.length === 0){
            flightsGrid.push(
                <Card.Grid key={-1} style={gridStyle}>Nenhum Voo Para A Cidade Desejada.</Card.Grid>
            );
        }

        this.setState({flights: flightsGrid, loading: false});
    }

    render() {
        return (
            <Card title={"Voos"} loading={this.state.loading}>
                {this.state.flights}
            </Card>
        )
    }
}
