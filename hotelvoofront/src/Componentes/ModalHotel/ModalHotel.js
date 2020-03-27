import React, { Component } from 'react'
import { Modal } from 'antd';
import HotelRoom from '../HotelRoom/HotelRoom';
import RoomBook from '../RoomBook/RoomBook';
import Axios from 'axios';

export default class ModalHotel extends Component {

    constructor(props){
        super(props);
        this.state = { confirmLoading: false, okText: "Ok", selRoom: {}, selMonths: []};
        this.handleModalClose = this.handleModalClose.bind(this);
        this.handleModalOk = this.handleModalOk.bind(this);
        this.handleHotelRoomClick = this.handleHotelRoomClick.bind(this);
        this.handleSelMonths = this.handleSelMonths.bind(this);
    }

    handleSelMonths(selMonths) {
        this.setState({selMonths: selMonths});
    }

    handleHotelRoomClick(id) {
        const roomInfoUrl = "http://localhost:8080/hotel/room/" + id;
        Axios.get(roomInfoUrl).then(res => {
            this.setState({ selRoom: res.data });
        });
    }

    handleModalClose() {
        this.props.onClose();
    }

    handleModalOk() {
        this.setState({confirmLoading: true, okText:"Processando"});
        setTimeout(() => {
            this.setState({visible: false, confirmLoading: false, okText: "Ok"});
            this.props.onOk(this.state.selRoom, this.state.selMonths);
        }, 2000);
    }

    render() {
        const confirmLoading = this.state.confirmLoading;
        const hotel = this.props.hotel;
        const visibleModal = this.props.visible;
        const okText = this.state.okText;
        const selRoom = this.state.selRoom;
        return (
            <Modal title={hotel.name} confirmLoading={confirmLoading} visible={visibleModal} onOk={this.handleModalOk} 
                        onCancel={this.handleModalClose} okText={okText}>
                
                <HotelRoom hotelId={hotel.id} onClick={this.handleHotelRoomClick} />
                <RoomBook key={selRoom.id} room={selRoom} onSelMonths={this.handleSelMonths} ></RoomBook>
            </Modal>
        );
    }
}
