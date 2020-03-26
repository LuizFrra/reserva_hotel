import React, { Component } from "react";
import monthsOptions from "./monthsOptions";
import { Checkbox, Row, Col, Divider } from "antd";

export default class RoomBook extends Component {
    constructor(props) {
        super(props);
        this.state = { monthsOptions: monthsOptions };
        console.log(monthsOptions);
    }

    componentDidMount() {
        const room = this.props.room;
        const monthsOptions = this.state.monthsOptions;
        const avaibleMonths = room.availableMonths;

        if (Object.entries(room).length === 0)
            return;

        for (let key in Object.entries(avaibleMonths)) {
            monthsOptions[avaibleMonths[key]].disabled = false;
        }

        // let checkBoxGroup = [];


        // monthsOptions.splice(0, 12).forEach(res => {
        //     checkBoxGroup.push(
        //         <Col key={res.value} span={5} offset={1}>
        //             <Checkbox value={res.value} disabled={res.disabled}>
        //                 {res.label}
        //             </Checkbox>
        //         </Col>
        //     );
        // });


        this.setState({ monthsOptions: checkBoxGroup });
    }

    render() {
        let months = this.state.monthsOptions;

        if (!React.isValidElement(months[0])) {
            return "";
        }

        return (
            <React.Fragment>
                <Divider> Meses </Divider>
                <Row>{months}</Row>
            </React.Fragment>
        );
    }
}
