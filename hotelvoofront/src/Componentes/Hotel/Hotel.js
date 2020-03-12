import React from 'react';

class Hotel extends React.Component {
    constructor(props){
        super(props);
    }

    render() {
        return (
            this.props.cityId
        );
    }
}

export default Hotel;