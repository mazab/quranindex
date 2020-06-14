import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import history from '../history';
import { doSearch, setLanguage } from '../actions';
import SearchBox from './SearchBox';

class NavBar extends React.Component {
    onSubmit = formValues => {
        let value = '';
        if (formValues.search && formValues.search.length > 0) {
            value = encodeURIComponent(formValues.search)
        }
        history.push(`/search/${value}`);
    };

    onLanguageChange = () => {
        this.props.setLanguage(this.props.language === 'ar' ? 'en' : 'ar');
    };

    renderLanguageChangeButtonText() {
        if (this.props.language === 'ar') {
            return "عربي";
        } else {
            return "English";
        }
    }

    renderTitle() {
        if (this.props.language === 'ar') {
            return "فهرس موضوعات القرآن";
        } else {
            return "Quran Topics Index";
        }
    }

    render() {
        return (
            <div className="ui fixed menu stackable">
                <Link className="header item" to={"/"} >
                    <h3>{this.renderTitle()}</h3>
                </Link>
                <div className="right menu">
                    <div className="item">
                        <div className="ui action left icon input">
                            <SearchBox onSubmit={this.onSubmit} />
                            <button className="ui button" onClick={this.onLanguageChange}>
                                {this.renderLanguageChangeButtonText()}
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        language: state.language
    };
};

export default connect(
    mapStateToProps,
    { doSearch, setLanguage }
)(NavBar);