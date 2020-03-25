import React from 'react';
import axios from 'axios';
import { Row } from 'antd';
import HotelCard from '../HotelCard/HotelCard';

export default class Hotel extends React.Component {

    constructor(props) {
        super(props);
        this.state = { hotels: [] };
    }

    componentDidMount() {
        const cityId = localStorage.getItem("cityId");
        const hotelByCityUrl = "http://localhost:8080/hotel/bycity/" + cityId;

        axios.get(hotelByCityUrl).then(res => {
            const hotels = res.data;
            let hotelsCards = [];

            for (let key in Object.entries(hotels)) {
                hotelsCards.push(
                    <HotelCard key={hotels[key].id} hotel={hotels[key]} />
                );
            }
            let hotelsRows = [];
            const numberOfRows = Math.ceil(hotelsCards.length / 3);
            for (var i = 1; i <= numberOfRows; i++) {
                let hotelsRow = [];
                hotelsCards.splice(0, 3).forEach((value, index) => {
                    hotelsRow.push(value);
                });
                
                hotelsRows.push(<Row key={i} justify={"space-around"}>{hotelsRow}</Row>);
            }

            this.setState({ hotels: hotelsRows });
        });
    }

    render() {
        const hotelsCards = this.state.hotels;
        return (
            <>
                {hotelsCards}
            </>
        );
    }
}
