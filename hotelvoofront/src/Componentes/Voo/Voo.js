import React, { Component } from 'react'
import axios from "axios";
import { Row } from "antd";
import FlyCompanyCard from '../FlyCompanyCard/FlyCompanyCard';
import ModalVoo from '../ModalVoo/ModalVoo';

export default class Voo extends Component {

    constructor(props) {
        super(props);
        this.state = {companies: [], airplanes: [], visible: false, flightsFromCompany: [], companyName: ""}
        this.handleCompanySelect = this.handleCompanySelect.bind(this);
        this.handleModalClose = this.handleModalClose.bind(this);
        this.handleModalOk = this.handleModalOk.bind(this);
    }

    handleModalClose() {
        this.setState({visible: false});
    }

    handleModalOk(flight) {
        this.setState({visible: false});
        this.props.onFLightSel(flight);
    }

    handleCompanySelect(companyName) {
        const airplanes = this.state.airplanes;
        var airplanesFromCompany = [];
        for(var airplane in Object.entries(airplanes)) {
            if(airplanes[airplane].flyCompany.name === companyName) {
                airplanesFromCompany.push(airplanes[airplane])
            }
        }

        this.setState({ flightsFromCompany: airplanesFromCompany, visible: true, companyName: companyName });
    }

    componentDidMount() {
        const cityId = localStorage.getItem("cityId");
        const vooByCityUrl = "http://localhost:8081/airplane/city/" + cityId;
        axios.get(vooByCityUrl).then(res => {
            var airplanes = res.data;

            const flycompanies = [];
            for(var airplane in Object.entries(airplanes)) {
                const companyName = airplanes[airplane].flyCompany.name;
                if(!flycompanies.includes(companyName))
                    flycompanies.push(companyName);
            }

            var flyCompaniesCards = [];
            for(var flyCompany in flycompanies) {
                flyCompaniesCards.push(
                    <FlyCompanyCard key={flyCompany} companyName={flycompanies[flyCompany]} onCompanySel={this.handleCompanySelect}>

                    </FlyCompanyCard>
                );
            }

            var companyRows = [];
            const numberOfRows = Math.ceil(flyCompaniesCards.length / 3);
            for (var i = 1; i <= numberOfRows; i++) {
                let companyRow = [];
                flyCompaniesCards.splice(0, 3).forEach((value, index) => {
                    companyRow.push(value);
                });

                companyRows.push(
                    <Row key={i} justify={"space-around"}>
                        {companyRow}
                    </Row>
                );
            }

            this.setState({companies: companyRows, airplanes: res.data});
        });
    }

    render() {
        const companiesCard = this.state.companies;
        const visibleModal = this.state.visible;
        const flights = this.state.flightsFromCompany;
        const companyName = this.state.companyName;
        return (
            <div>
                <React.Fragment>
                    {companiesCard}
                    <ModalVoo months={this.props.months} flights={flights} companyName={companyName} visible={visibleModal} onClose={this.handleModalClose} 
                        onOk={this.handleModalOk}/>
                </React.Fragment>
            </div>
        )
    }
}
