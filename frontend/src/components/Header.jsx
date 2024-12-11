import React, {useCallback} from 'react';
import { Link } from 'react-router-dom';
import { buergerLogout } from "../actions/auth-buerger";
import { mitarbeiterLogout } from "../actions/auth-mitarbeiter";
import { useSelector, useDispatch } from "react-redux";
import logo from "../assests/Logo.png"

const Header = () => {

    const dispatch = useDispatch();
    const { user: currentUser } = useSelector((state) => state.auth);

    const logOut = useCallback(() => {
        if (currentUser.role === "BUERGER"){
            dispatch(buergerLogout());
        } else {
            dispatch(mitarbeiterLogout())
        }
    }, [dispatch]);

    return (
        <header>
            <div style={{display: "flex", gap: "8px"}}>
                <img src={logo} width="40px" height="40px"/>
                <p style={{fontSize: "24px", fontWeight: "bold", color: "white", userSelect: "none"}}>CityFeedback</p>
            </div>
            <nav>
                <p>{currentUser.email}</p>
                <Link className="navItem" to="/anmelden" onClick={logOut}>Logout</Link>
            </nav>
        </header>
    );
};

export default Header;