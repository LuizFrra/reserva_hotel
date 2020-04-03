import React, { Component } from 'react'
import { Modal } from 'antd';
import CompanyFlights from '../CompanyFlights/CompanyFlights';

export default class ModalVoo extends Component {

    constructor(props){
        super(props);
        this.state = { confirmLoading: false, okText: "Ok", selFlight: "", okDisabled: true};
        this.handleModalClose = this.handleModalClose.bind(this);
        this.handleModalOk = this.handleModalOk.bind(this);
        this.handleFlightSel = this.handleFlightSel.bind(this);
    }


    handleModalClose() {
        this.props.onClose();
    }

    handleModalOk() {
        this.setState({confirmLoading: true, okText:"Processando"});
        setTimeout(() => {
            this.setState({confirmLoading: false, okText: "Ok"});
            this.props.onOk(this.state.selFlight);
        }, 2000);
    }

    handleFlightSel(flight, disable) {
        //console.log(flight);
        this.setState({selFlight: flight, okDisabled: disable})
    }

    render() {
        const confirmLoading = this.state.confirmLoading;
        const companyName = this.props.companyName;
        const visibleModal = this.props.visible;
        const okText = this.state.okText;
        const flights = this.props.flights;
        const okDisabled = this.state.okDisabled;
        return (
            <Modal title={companyName} confirmLoading={confirmLoading} visible={visibleModal} onOk={this.handleModalOk} 
                        onCancel={this.handleModalClose} okText={okText} okButtonProps={{ disabled: okDisabled }}>
                <CompanyFlights months={this.props.months} key={Math.random()} onClick={this.handleFlightSel} flights={flights} />
            </Modal>
        );
    }
}
