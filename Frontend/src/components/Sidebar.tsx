import {
    CSidebar,
    CSidebarBrand,
    CSidebarHeader,
    CSidebarNav,
    CNavItem,
    CNavTitle,
} from '@coreui/react'
import { PiStudent } from "react-icons/pi";
import { CgSupport } from "react-icons/cg";

const Sidebar = () => {
    return (
        <CSidebar className="border-end sidebar" colorScheme="dark">
            <CSidebarHeader className="border-bottom">
                <CSidebarBrand><CgSupport style={{ marginRight: '8px' }} />
                    Förderratgeber</CSidebarBrand>
            </CSidebarHeader>
            <CSidebarNav>
                <CNavTitle>Förderungen</CNavTitle>
                <CNavItem href="#">
                    <PiStudent style={{ marginRight: '8px' }} />
                    Studentenförderung
                </CNavItem>
            </CSidebarNav>
        </CSidebar>
    )
}

export default Sidebar;