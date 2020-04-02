import React from "react";
import axios from "axios";
import { Row } from "antd";
import HotelCard from "../HotelCard/HotelCard";
import ModalHotel from "../ModalHotel/ModalHotel";

export default class Hotel extends React.Component {
    constructor(props) {
        super(props);
        this.state = { hotels: [], selHotelCard: {}, visibleModal: false};
        this.handleHotelCardClick = this.handleHotelCardClick.bind(this);
        this.handleModalClose = this.handleModalClose.bind(this);
        this.handleModalOk = this.handleModalOk.bind(this);
    }

    handleHotelCardClick(hotel) {
        this.setState({selHotelCard: hotel, visibleModal: true});
    }

    handleModalClose() {
        this.setState({visibleModal: false});
    }

    handleModalOk(roomId, books) {
        this.setState({visibleModal: false});
        this.props.onHotelBook(roomId.id, books);
    }

    componentDidMount() {
        const cityId = localStorage.getItem("cityId");
        const hotelByCityUrl = "http://localhost:8080/hotel/bycity/" + cityId;

        axios.get(hotelByCityUrl).then(res => {
            const hotels = res.data;
            let hotelsCards = [];

            for (let key in Object.entries(hotels)) {
                hotelsCards.push(
                    <HotelCard key={hotels[key].id} hotel={hotels[key]} handleHotelCardClick={this.handleHotelCardClick} />
                );
            }
            let hotelsRows = [];
            const numberOfRows = Math.ceil(hotelsCards.length / 3);
            for (var i = 1; i <= numberOfRows; i++) {
                let hotelsRow = [];
                hotelsCards.splice(0, 3).forEach((value, index) => {
                    hotelsRow.push(value);
                });

                hotelsRows.push(
                    <Row key={i} justify={"space-around"}>
                        {hotelsRow}
                    </Row>
                );
            }

            this.setState({ hotels: hotelsRows });
        });
    }

    render() {
        const hotelsCards = this.state.hotels;
        const visibleModal = this.state.visibleModal;
        const selHotel = this.state.selHotelCard;
        return (
            <React.Fragment>
                {hotelsCards}
                <ModalHotel key={selHotel.id} visible={visibleModal} hotel={selHotel} onClose={this.handleModalClose} 
                        onOk={this.handleModalOk}/>
            </React.Fragment>
        );
    }
}
