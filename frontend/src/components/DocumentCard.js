import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import style from './DocumentCard.module.css'

class DocumentCard extends React.Component {
    renderName() {
        const propName = this.props.language === 'ar' ? 'arName' : 'enName';
        return this.props.entry[propName];
    }

    renderContent() {
        const propName = this.props.language === 'ar' ? 'arText' : 'enText';
        if (this.props.entry[propName]) {
            return this.props.entry[propName];
        }
    }

    render() {
        return (
            <Link to={`/document/${this.props._id}`} className={`ui label ${style.documentCard}`}>
                <div className="content">
                    <div className={`description ${style.documentDescription}`}>{this.renderName()}</div>
                    <div className={`description ${style.documentDescription}`}>{this.renderContent()}</div>
                </div>
            </Link>
        );
    }
}

const mapStateToProps = state => {
    return {
        language: state.language
    };
};

export default connect(
    mapStateToProps,
    {}
)(DocumentCard);