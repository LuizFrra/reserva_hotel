import React, { Component } from "react";
import monthsOptions from "./monthsOptions";
import { Checkbox, Row, Col, Divider } from "antd";

export default class RoomBook extends Component {
    constructor(props) {
        super(props);
        this.state = { options: [] };
        this.handleCheckChange = this.handleCheckChange.bind(this);
    }

    handleCheckChange(checkedValue) {
        this.props.onSelMonths(checkedValue);
    }

    componentDidMount() {
        const room = this.props.room;
        const availableMonths = room.availableMonths;
        let options = [];
        
        if (Object.entries(room).length === 0)
            return;

        for(let index in  monthsOptions) {
            options.push(Object.assign({}, monthsOptions[index]));
        }

        for (let key in Object.entries(availableMonths)) {
            options[availableMonths[key]].disabled = false;
        }   

        let checkBoxGroup = [];
        for(let index in options) {
            checkBoxGroup.push(
                <Col key={index} span={7} offset={1}>
                    <Checkbox value={options[index].value} disabled={options[index].disabled}>
                        {options[index].label}
                    </Checkbox>
                </Col>
            );
        }

        this.setState({ options: checkBoxGroup });
    }

    render() {
        let months = this.state.options;

        if (!React.isValidElement(months[0])) {
            return (<React.Fragment></React.Fragment>);
        }
        return (
            <React.Fragment>
                <Divider> Meses </Divider>
                <Checkbox.Group onChange={this.handleCheckChange} style={{marginLeft: 60}}>
                    <Row>{months}</Row>
                </Checkbox.Group>
            </React.Fragment>
        );
    }
}
