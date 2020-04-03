import React, { Component } from 'react'
import { Row, Result } from 'antd';
import axios from 'axios';
export default class ResultPage extends Component {

    constructor(props) {
        super(props);
        this.state = { renderResult: this.renderSuccess() }
    }

    componentDidMount() {
        const months = Array.from(localStorage.getItem("months").split(','), Number);
        const room = localStorage.getItem("room");
        const flight = localStorage.getItem("flight");
        const urlPostRoom = "http://localhost:8080/hotel/bookroom";
        var roomObj = {HotelRoomId: room, Month: 0 };

        for(var item in Object.entries(months)) {
            var objCopy = Object.assign({}, roomObj);
            objCopy.Month = months[item];
            axios.post(urlPostRoom, objCopy).then(res => {
            }).catch(fail => {
                console.log(fail.response.data);
                this.setState({ renderResult: this.renderFail() })
            });
        }
        var flightObj = { airplaneId: flight, month: 0 };
        for(var item in Object.entries(months)) {
            var objCopy = Object.assign({}, flightObj);
            objCopy.month = months[item];
            axios.post("http://localhost:8081/flightbook", flightObj).then(res => {
            }).catch(fail => {
                console.log(fail.response.data);
                this.setState({ renderResult: this.renderFail() })
            });
        }
    }

    renderSuccess() {
        return (
            <Result status="success" title="Compras Efetuadas com Sucesso !!!" subTitle="Boa Viagem, Divirta-se !!!">

            </Result>
        );
    }

    renderFail() {
        return (
            <Result status="warning" title="Ocorreu algum problema durante a solicitação.">

            </Result>
        );
    }

    render() {
        return (
            <React.Fragment>
                <Row justify="space-around">
                    {this.state.renderResult}
                </Row>                
            </React.Fragment>
        );
    }
}
