import {
    CSidebar,
    CSidebarBrand,
    CSidebarHeader,
    CSidebarNav,
    CNavItem,
    CNavTitle,
} from '@coreui/react'
import { PiStudent } from "react-icons/pi";


const Sidebar = () => {
    return (
        <CSidebar className="border-end" colorScheme="dark">
            <CSidebarHeader className="border-bottom">
                <CSidebarBrand>Förderratgeber</CSidebarBrand>
            </CSidebarHeader>
            <CSidebarNav>
                <CNavTitle>Förderungen</CNavTitle>
                <CNavItem href="#">
                    <PiStudent style={{ marginRight: '8px' }} />
                    Studenten
                </CNavItem>
            </CSidebarNav>
        </CSidebar>
    )
}

export default Sidebar;
