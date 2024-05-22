import { Tab } from '@headlessui/react';
import { Fragment, useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Dialog, Transition } from '@headlessui/react';
import Tippy from '@tippyjs/react';
import { mappingStudent, listingStudent, getStudentInfoById } from '../../service/parent';
import { getSchoolNotice, getSchoolNoticeDetail, getSchoolInfo } from '../../service/school';
import { getClassInfo } from '../../service/class';
import { getSubjectList } from '../../service/subject';
import { getUserInfobyId } from '../../service/auth';
import IconCode from '../../components/Icon/IconCode';
import IconHome from '../../components/Icon/IconHome';
import IconUser from '../../components/Icon/IconUser';
import IconPhone from '../../components/Icon/IconPhone';
import IconInfoCircle from '../../components/Icon/IconInfoCircle';
import IconTrashLines from '../../components/Icon/IconTrashLines';
import IconX from '../../components/Icon/IconX';
import IconAt from '../../components/Icon/IconAt';
import IconLock from '../../components/Icon/IconLock';
import IconDownload from '../../components/Icon/IconDownload';
import IconGallery from '../../components/Icon/IconGallery';
import Dropdown from '../../components/Dropdown';
import IconArrowLeft from '../../components/Icon/IconArrowLeft';
import IconPrinter from '../../components/Icon/IconPrinter';
import IconFolder from '../../components/Icon/IconFolder';
import IconZipFile from '../../components/Icon/IconZipFile';
import IconTxtFile from '../../components/Icon/IconTxtFile';

interface ClassInfoType {
    class: {
        grade: string;
        inClass: string;
    };
    users: Array<{ userId: string; userName: string; name: string }>;
}

interface UserInfoType {
    name: string;
    phoneNumber: string;
}

const ChooseSubject = () => {

    const [modal21, setModal21] = useState(false);
    const [userCode, setUserCode] = useState('');
    const [studentList, setStudentList] = useState([]);
    const [noticeList, setNoticeList] = useState([]);
    const [targetStudentInfo, setTargetStudentInfo] = useState('');
    const [showModal, setShowModal] = useState(false);
    const [selectedNotice, setSelectedNotice] = useState('');

    const [classInfo, setClassInfo] = useState<ClassInfoType>({
        class: { grade: '', inClass: '' },
        users: []
    });

    const [userInfo, setUserInfo] = useState<UserInfoType>({ name: '', phoneNumber: '' });

    useEffect(() => {
        const getUserInfo = async () => {
            try {
                const data = await getUserInfobyId(localStorage.getItem('id'));
                console.log(data);    
                setUserInfo(data);
            } catch (error) {
                console.error('Error get UserInfo', error);
            }
        };
        getUserInfo();
    }, []);

    useEffect(() => {
        const fetchSubject = async () => {
            try { 
                const data = await getSubjectList();
                console.log(data);
            } catch (error) {
                console.error('Error fetching student list:', error);
            }
        };
        fetchSubject();
    }, []);
    
    useEffect(() => {
        const fetchNoticeData = async () => {
            try {
                const data = await getSchoolNotice(localStorage.getItem('TargetStudent'));
                setNoticeList(data);
                console.log(data);    
            } catch (error) {
                console.error('Error fetching student data:', error);
            }
        };
        fetchNoticeData();
    }, []);

    useEffect(() => {
        const fetchClassData = async () => {
            try {
                const data = await getClassInfo(localStorage.getItem('classId'));
                console.log(data);    
                setClassInfo(data);
            } catch (error) {
                console.error('Error fetching class data:', error);
            }
        };
        fetchClassData();
    }, []);

  
     
    return (
        <div>
            <ul className="flex space-x-2 y-3 rtl:space-x-reverse">
                <li>
                    <Link to="/teacher/home" className="text-primary hover:underline">
                        교사
                    </Link>
                </li>
                <li className="before:content-['/'] ltr:before:mr-2 rtl:before:ml-2">
                    <span>반 관리</span>
                </li>
                <li className="before:content-['/'] ltr:before:mr-2 rtl:before:ml-2">
                    <span>과목 목록</span>
                </li>
            </ul>
            <div className="active pt-5">
                <div>
                    <form className="border border-[#ebedf2] dark:border-[#191e3a] rounded-md p-4 mb-5 bg-white dark:bg-black">
                        <h6 className="text-lg font-bold mb-5">과목 목록</h6>
                        <div className="flex flex-col sm:flex-row">
                            <div className="flex-1 grid grid-cols-1 gap-5">
                                <div className='w-1/2'>
                                    <label htmlFor="id">담임</label>
                                    <div className="form-input">{userInfo.name}</div>
                                </div>
                                <div>
                                </div>
                                <div>
                                    <label htmlFor="email">과목 목록</label>
                                    <div className="form-input">
                                    <div className="table-responsive mb-5">
                                    <div className="panel">
                                    <div className="table-responsive mb-5">
                                        <table>
                                            <thead>
                                                <tr>
                                                    <th>이름</th>
                                                    <th>학교</th>
                                                    <th>생년월일</th>
                                                    <th>등록일</th>
                                                    <th className="text-center">선택</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {studentList.map((data: any) => {
                                                    return (
                                                        <tr key={data.id}>
                                                            <td>
                                                                <div className="whitespace-nowrap">{data.user.name}</div>
                                                            </td>
                                                            <td>{data.user.schoolName}</td>
                                                            <td>
                                                                {Math.floor(data.user.birthdate / 10000) > 40 ? (
                                                                    <>
                                                                        19{Math.floor(data.user.birthdate / 10000)}년 {Math.floor((data.user.birthdate / 100) % 100)}월 {Math.floor(data.user.birthdate % 100)}일
                                                                    </>
                                                                ) : (
                                                                    Math.floor(data.user.birthdate / 10000) < 10 ? (
                                                                        <>
                                                                            200{Math.floor(data.user.birthdate / 10000)}년 {Math.floor((data.user.birthdate / 100) % 100)}월 {Math.floor(data.user.birthdate % 100)}일
                                                                        </>
                                                                    ) : (
                                                                        <>
                                                                            20{Math.floor(data.user.birthdate / 10000)}년 {Math.floor((data.user.birthdate / 100) % 100)}월 {Math.floor(data.user.birthdate % 100)}일
                                                                        </>
                                                                    )
                                                                )}
                                                            </td>
                                                            <td>{data.regDate[0]}년 {data.regDate[1]}월 {data.regDate[2]}일</td>
                                                            <td className="text-center">
                                                                <Tippy content="전환">
                                                                    <button type="button" onClick={() => handleChange(data.user.userId)}>
                                                                        <IconTrashLines className="m-auto" />
                                                                    </button>
                                                                </Tippy>
                                                            </td>
                                                        </tr>
                                                    );
                                                })}
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                    </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );

}

export default ChooseSubject;