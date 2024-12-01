import Registrieren from './pages/registrierenBuerger.js'
import Dashboard from './pages/mitarbeiterDashboard.js'
import './App.css';
import './style/styles.css';
import { BrowserRouter, Routes, Route} from "react-router-dom";
import MitarbeiterDashboard from "./pages/mitarbeiterDashboard.js";
import BeschwerdeDetail from "./pages/beschwerdeDetail";
import LoginBuerger from "./pages/loginBuerger";
import BeschwerdeForm from "./pages/beschwerdeErstellen";
import BuergerDashboard from "./pages/buergerDashboard";

function App() {
  return (
    <div className="App">
        <BrowserRouter>
            <Routes>
                <Route path="/buerger-registrieren" element={<Registrieren />}/>
                <Route path="/mitarbeiter/dashboard" element={<MitarbeiterDashboard />} />
                <Route path="/mitarbeiter/dashboard/:id" element={<BeschwerdeDetail />} />
                <Route path="/buerger-anmelden" element={<LoginBuerger />}/>
                <Route path="/neuebeschwerde" element={<BeschwerdeForm />}/>
                <Route path="/buerger/dashboard" element={<BuergerDashboard />}/>
            </Routes>
        </BrowserRouter>
    </div>
  );
}

export default App;
